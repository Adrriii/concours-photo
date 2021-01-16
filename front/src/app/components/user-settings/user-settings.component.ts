import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/User.model';
import { AuthService } from 'src/app/services/auth.service';
import { EditSettingsComponent } from './edit-settings/edit-settings.component';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit, OnDestroy {

    currentUserSubscription: Subscription;
    currentUser: User = null;

    constructor(
        private dialog: MatDialog,
        private authService: AuthService
    ) {
    }

    ngOnInit(): void {
        this.currentUserSubscription = this.authService.me.subscribe(
            user => this.currentUser = user
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
      dialogConfig.width = '30%';

      const dialogRef = this.dialog.open(EditSettingsComponent, dialogConfig);

      dialogRef.afterClosed().subscribe(
          data => console.log('Dialog output:', data)
      );
    }

    getUserName() : string {
      if(this.currentUser)
          return this.currentUser.username;
      return "Undefined";
    }

    getSetting(settingName : string) : string {
      if(this.currentUser && this.currentUser.settings.has(settingName))
          return this.currentUser.settings.get(settingName)
      return "Undefined";
    }
}
