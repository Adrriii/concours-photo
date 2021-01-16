import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {sha256} from 'js-sha256';
import {UserAuth} from '../models/UserAuth.model';
import {environment} from '../../environments/environment.prod';
import {Subject, Subscription} from 'rxjs';
import {User} from '../models/User.model';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    public currentUser: User = null;
    public me = new Subject<User>();
    public isAuth = false;

    constructor(private httpClient: HttpClient) {
    }

    emitMe(): void {
        this.me.next(this.currentUser);
    }

    private setCurrentUser(jwt): void {
        this.isAuth = true;
        localStorage.setItem('jwt', jwt);

        this.httpClient.get<User>(environment.apiBaseUrl + 'user/me').subscribe(
            user => {
                console.log(user);
                // let test : User = user;
                // console.log(test);                
                this.currentUser = User.fromJson(user);
                // this.currentUser = user;
                this.emitMe();

                console.log('Successfully get current user : ' + JSON.stringify(this.currentUser));
            }, error => {
                console.log('Error while getting logged user : ' + error.message);
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

    createNewUser(username: string, password: string): Promise<void> {
        return new Promise<void>(
            (resolve, reject) => {
                this.httpClient
                    .post(environment.apiBaseUrl + 'register',
                        {
                            username,
                            passwordHash: sha256(password)
                        })
                    .subscribe(
                        () => {
                            console.log('user created successfully !');
                            resolve();
                        },
                        (error) => {
                            console.log('error in create user : ' + error);
                            reject(error);
                        }
                    );
            }
        );
    }

    logInUser(username: string, password: string): Promise<void> {
        return new Promise<void>(
            (resolve, reject) => {
                this.httpClient
                    .post(environment.apiBaseUrl + 'login',
                        {
                            username,
                            passwordHash: sha256(password)
                        }, {
                            responseType: 'text'
                        })
                    .subscribe(
                        data => {
                            console.log('user logged successfully, data is : ' + data);
                            this.setCurrentUser(data);
                            resolve();
                        },
                        error => {
                            console.log('Error in user log : ' + JSON.stringify(error));
                            reject();
                        }
                    );
            }
        );
    }

    logOutUser(): Promise<void> {
        return new Promise<void>(
            (resolve, reject) => {
                this.httpClient
                    .get<any[]>(environment.apiBaseUrl + 'logout')
                    .subscribe(
                        () => {
                            console.log('user logged out successfully');
                            this.clearCurrentUser();
                            resolve();
                        },
                        error => {
                            console.log('Error in logout : ' + JSON.stringify(error));
                            reject(error);
                        }
                    );
            }
        );
    }
}
