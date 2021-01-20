import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Theme } from 'src/app/models/Theme.model';
import { ThemeService } from 'src/app/services/theme.service';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatRadioModule } from '@angular/material/radio';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.css']
})
export class ThemeComponent implements OnInit {

    nextThemesSubscription: Subscription;
    nextThemes: Array<Theme>;
    chosenTheme: number = null;

    constructor(
      private themeService: ThemeService,
      private authService: AuthService) { }

    ngOnInit(): void {
        this.themeService.getNextThemes()
            .subscribe(
                (nextThemes) => {
                  this.nextThemes = nextThemes;
                }
            );
        console.log(this.nextThemes);
    }

    vote(): void {
        if(this.chosenTheme){
            this.themeService.voteTheme(this.chosenTheme)
                .subscribe(
                    () => console.log("sucessfully voted"),
                    (error) => console.log("an error " + error.message)
                );
        }
    }

    isAuth(): boolean {
        return this.authService.isAuth;
    }

    hasVoted(): boolean {
        if(this.authService.currentUser)
            return this.authService.currentUser.theme === null;
        return false;
    }
}
