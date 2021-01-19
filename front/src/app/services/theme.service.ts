import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Theme} from '../models/Theme.model';
import {environment} from '../../environments/environment.prod';

@Injectable({
    providedIn: 'root'
})
export class ThemeService {

    constructor(
        private httpClient: HttpClient
    ) {
    }

    getCurrentTheme(): Observable<Theme> {
        return this.httpClient.get<Theme>(environment.apiBaseUrl + `themes/current`);
    }

    getNextThemes(): Observable<Array<Theme>> {
        return this.httpClient.get<Array<Theme>>(environment.apiBaseUrl + `themes/proposals`);
    }

    voteTheme(themeId: number): void {
        this.httpClient.post(environment.apiBaseUrl + `themes/proposals/${themeId}`, {})
            .subscribe(
                () => console.log('User voted theme successfully added'),
                (error) => console.log('Error in voteTheme : ' + error.message)
            );
    }

}
