import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {sha256} from 'js-sha256';
import {UserAuth} from '../models/UserAuth.model';
import {environment} from '../../environments/environment.prod';
import {Subject, Subscription} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private currentUser: UserAuth = null;
    public me = new Subject<UserAuth>();

    constructor(private httpClient: HttpClient) {
    }

    emitMe(): void {
        this.me.next(this.currentUser);
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
                            this.currentUser = new UserAuth(username);
                            this.emitMe();
                            localStorage.setItem('jwt', data);
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
                            this.currentUser = null;
                            localStorage.clear();

                            this.emitMe();
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
