import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Theme } from 'src/app/models/Theme.model';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.css']
})
export class ThemeComponent implements OnInit {

    nextThemesSubscription: Subscription;
    nextThemes: Array<Theme>;

    constructor(private themeService: ThemeService) { }

    ngOnInit(): void {
        this.themeService.getNextThemes()
            .subscribe(
                (nextThemes) => {
                  this.nextThemes = nextThemes;
                  console.log("next themes "+JSON.stringify(nextThemes));
                  
                }
            );
        console.log(this.nextThemes);
    }

}
