import { Component, OnInit } from '@angular/core';
import { Theme } from 'src/app/models/Theme.model';
import { ThemeService } from 'src/app/services/theme.service';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatRadioModule } from '@angular/material/radio';
import { AuthService } from 'src/app/services/auth.service';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/User.model';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.css']
})
export class ThemeComponent implements OnInit {

    currentUserSubscription: Subscription;
    currentUser: User;
    nextThemes: Array<Theme>;
    chosenTheme: Theme = null;
    currentUserTheme: number = null;

    constructor(
      private themeService: ThemeService,
      private authService: AuthService) { }

    ngOnInit(): void {
        this.themeService.getNextThemes()
            .subscribe(
                (nextThemes) => {
                    this.nextThemes = nextThemes;
                    if (this.authService.currentUser)
                        this.currentUserTheme = this.authService.currentUser.theme;
                }
            );
    }

    vote(): void {
        if(this.chosenTheme){
            this.deleteVote();
            this.chosenTheme.nbVotes++;
            this.themeService.voteTheme(this.chosenTheme.id)
                .subscribe(
                    () => {
                        console.log("Theme sucessfully voted");
                        this.currentUserTheme = this.chosenTheme.id;
                        this.authService.currentUser.theme = this.chosenTheme.id;
                    },
                    (error) => console.log("Error in vote() in theme component : " + error.message)
                );
        }
    }

    deleteVote(): void {
        if(this.currentUserTheme){
            this.nextThemes.find(theme => theme.id === this.currentUserTheme)
                .nbVotes--;
            this.themeService.deleteVoteTheme()
                .subscribe(
                    () => console.log("Vote sucessfully deleted"),
                    (error) => console.log("Error in deleteVote() in theme component : " + error.message)
                );
        }
    }

    isAuth(): boolean {
        return this.authService.isAuth;
    }

    isActualChoice(themeId: number): boolean {
        return this.currentUserTheme === themeId;
    }

    isVotable(): boolean {
        if(this.chosenTheme){
            return this.currentUserTheme !== this.chosenTheme.id;
        }
        return false;
    }

    votePercentage(nbVotes: number): number {
        let totalVotes: number = 0;
        this.nextThemes.forEach(theme => totalVotes += theme.nbVotes);
        let percentage: number = nbVotes/totalVotes*100;
        return Math.round(percentage);
    }
}
