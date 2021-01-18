import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { NgxFileDropEntry } from 'ngx-file-drop';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/User.model';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from '../../services/auth.service';
import { EditSettingsComponent } from './edit-settings/edit-settings.component';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';

@Component({
    selector: 'app-user-settings',
    templateUrl: './user-settings.component.html',
    styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit, OnDestroy {

    currentUser: User = null;
    currentUserSubscription: Subscription;
    file: File;

    constructor(
        private dialog: MatDialog,
        private authService: AuthService,
        private toastr: ToastrService,
        private userService: UserService
    ) {
    }

    ngOnInit(): void {
        this.currentUserSubscription = this.authService.me.subscribe(
            user => {
              this.currentUser = user;
            }
        );

    }

    ngOnDestroy(): void {
        this.currentUserSubscription.unsubscribe();
    }

    openDialog(): void {
        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.backdropClass = 'backdropBackground';
        dialogConfig.width = '80%';
        dialogConfig.maxHeight = '80vh';

        const dialogRef = this.dialog.open(EditSettingsComponent, dialogConfig);

        dialogRef.afterClosed().subscribe(
            data => console.log('Dialog output:', data)
        );
    }

    getUserName() : string {
        if(this.authService.currentUser)
            return this.authService.currentUser.username;
        return "Undefined";
    }

    getSetting(settingName: string): string{
        if(this.authService.currentUser)
          return this.authService.currentUser.getSetting(settingName);
        return null
    } 

    onFileSelect(event): void{
        this.file = event.target.files[0];
        this.upload();
    }

    upload(): void{
        const uploadPicture = new FormData();
        uploadPicture.append('file', this.file, this.file.name);
        this.userService.updateProfilePicture(uploadPicture)
            .subscribe(
                (user) => {
                    console.log('picture successfully uploaded : '+ JSON.stringify(user));
                    location.reload();
                },
                (error) => {
                    this.toastr.error(error.message);
                }
            );
    }

    getPhoto(): string {
        if(this.authService.currentUser)
          return this.authService.currentUser.photo;
        return null;
    }

    deletePhoto(): void {
        if(this.authService.currentUser){
          this.authService.currentUser.photo = null;
          this.authService.currentUser.photoDelete = null;
        }
    }
}
