import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { sha256 } from 'js-sha256';
import { User } from '../models/User.model';
import {environment} from '../../environments/environment.prod';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    public me: User;

    constructor(private httpClient: HttpClient) { }

    createNewUser(username: string, password: string): Promise<void> {
        return new Promise<void>(
            (resolve, reject) => {
                this.httpClient
                    .post(environment.apiBaseUrl + 'register',
                        {
                            username,
                            passwordHash : sha256(password)
                        })
                    .subscribe(
                        () => {
                            console.log('user created successfully !');
                            this.me = new User(username);
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
                            passwordHash : sha256(password)
                        }, {
                            responseType: 'text'
                        })
                    .subscribe(
                        data => {
                            console.log('user logged successfully, data is : ' + data);
                            this.me = new User(username);
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

    logOutUser(username: string, password: string): Promise<void> {
        return new Promise<void>(
            (resolve, reject) => {
                this.httpClient
                    .get<any[]>(environment.apiBaseUrl + 'logout')
                    .subscribe(
                        (Response) => {
                            console.log('user logged out successfully');
                            this.me = null;
                            localStorage.clear();
                            resolve();
                        },
                        (error) => {
                            console.log('error');
                            reject();
                        }
                    );
            }
        );
    }
}
