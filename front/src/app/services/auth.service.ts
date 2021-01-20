import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {sha256} from 'js-sha256';
import {UserAuth} from '../models/UserAuth.model';
import {environment} from '../../environments/environment.prod';
import {Subject, Subscription} from 'rxjs';
import {User} from '../models/User.model';
import {Observable} from 'rxjs/internal/Observable';
import {catchError, map, tap} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    public currentUser: User = null;
    public me = new Subject<User>();
    public isAuth = false;

    constructor(private httpClient: HttpClient) {
        const jwt = localStorage.getItem('jwt');

        if (jwt !== null) {
            this.setCurrentUser(jwt);
        }
    }

    emitMe(): void {
        this.me.next(this.currentUser);
    }

    private setCurrentUser(jwt): void {
        this.isAuth = true;
        localStorage.setItem('jwt', jwt);

        this.httpClient.get<User>(environment.apiBaseUrl + 'user/me').subscribe(
            user => {
                this.currentUser = User.fromJson(user);
                this.emitMe();
            }, () => {
                this.clearCurrentUser();
            }
        );
    }

    private clearCurrentUser(): void {
        this.currentUser = null;
        this.isAuth = false;
        localStorage.clear();

        this.emitMe();
    }

    createNewUser(username: string, password: string, email: string): Observable<any> {
        return this.httpClient.post(
            environment.apiBaseUrl + 'register',
            {
                username,
                passwordHash: sha256(password),
                email
            });
    }

    logInUser(username: string, password: string): Observable<any> {
        return this.httpClient
            .post(environment.apiBaseUrl + 'login',
                {
                    username,
                    passwordHash: sha256(password)
                }, {
                    responseType: 'text'
                })
            .pipe(
                tap(token => this.setCurrentUser(token))
            );
    }

    logOutUser(): Observable<any> {
        return this.httpClient
            .get<any[]>(environment.apiBaseUrl + 'logout')
            .pipe(
                tap(
                    () => this.clearCurrentUser(),
                    () => this.clearCurrentUser()
                )
            );
    }
}
