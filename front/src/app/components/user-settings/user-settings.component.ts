import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { NgxFileDropEntry } from 'ngx-file-drop';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/User.model';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from '../../services/auth.service';
import { EditSettingsComponent } from './edit-settings/edit-settings.component';

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
        console.log('on init => ' + this.currentUser);

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

    getUserName(): string {
      if (this.authService.currentUser) {
          return this.authService.currentUser.username;
      }
      return 'Undefined';
    }

    getSetting(settingName: string): string {
        return this.authService.currentUser.getSetting(settingName);
    }

    onFileChanged(event): void{
        this.file = event.target.files[0];
    }

    onUpload(): void {
        const uploadPicture = new FormData();
        uploadPicture.append('file', this.file, this.file.name);
        this.userService.updateProfilePicture(uploadPicture)
            .subscribe(
                (user) => {
                    console.log('picture successfully uploaded : ' + JSON.stringify(user));
                    // console.log(user)
                    // console.log(user);
                    // this.currentUser = User.fromJson(user);
                },
                (error) => {
                    this.toastr.error(error.message);
                }
            );
    }

    getPhoto(): string {
      return this.authService.currentUser.photo;
    }
}
