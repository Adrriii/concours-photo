import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { User } from '../../../models/User.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-edit-settings',
  templateUrl: './edit-settings.component.html',
  styleUrls: ['./edit-settings.component.css']
})
export class EditSettingsComponent implements OnInit, OnDestroy {
    [x: string]: any;

    currentUser: User = null;
    currentUserSubscription: Subscription;
    form : FormGroup;

    constructor(
        private dialogRef: MatDialogRef<EditSettingsComponent>,
        @Inject(MAT_DIALOG_DATA) data,
        private dialog: MatDialog,
        private authService: AuthService,
        private userService: UserService,
        private formBuilder: FormBuilder
    ) {
    }

    ngOnInit(): void {
        this.currentUserSubscription = this.authService.me.subscribe(
            user => {
                this.currentUser = user
            }
        );
        console.log(this.currentUserSubscription);
        
        this.initForm();
    }

    initForm() {
        this.currentUser = this.authService.currentUser;
        this.form = this.formBuilder.group({
            username: [this.currentUser.username,Validators.required],
            mail: this.currentUser.getSetting('MAIL'),
            gender: this.currentUser.getSetting('GENDER'),
            birthday: this.currentUser.getSetting('BIRTHDAY'),
            location: this.currentUser.getSetting('LOCATION'),
            bio: this.currentUser.getSetting('BIO')
        });
    }

    ngOnDestroy(): void {
        this.currentUserSubscription.unsubscribe();
    }

    update(): void {
        this.currentUser = this.authService.currentUser;
        this.currentUser.username = this.form.value.username;
        this.currentUser.setSetting('MAIL',this.form.value.mail);
        this.currentUser.setSetting('GENDER',this.form.value.gender);
        this.currentUser.setSetting('BIRTHDAY',this.form.value.birthday);
        this.currentUser.setSetting('LOCATION',this.form.value.location);
        this.currentUser.setSetting('BIO',this.form.value.bio);

        console.log(this.currentUser);
        
        this.userService.update(this.currentUser)
            .subscribe(
              (user) => {
                console.log(user);
              },
              (error) => {
                console.log(error);
              }
            )

        this.dialogRef.close();
    }

    close(): void {
        this.dialogRef.close();
    }

    getSetting(settingName: string): string{
        return this.authService.currentUser.getSetting(settingName);
      }

    isCurrentGender(gender: string): boolean {
        return this.authService.currentUser.getSetting('GENDER') === gender;
    }

    isFormValid(): boolean {
        return this.form.valid;
    }

}
