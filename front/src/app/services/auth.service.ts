import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { sha256 } from 'js-sha256';
import { User } from '../models/User.model';

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
                    .post('http://localhost:9000/api/v1/register',
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
                    .post('http://localhost:9000/api/v1/login',
                        {
                            username,
                            passwordHash : sha256(password)
                        })
                    .subscribe(
                        () => {
                            console.log('user logged successfully');
                            this.me = new User(username);
                            resolve();
                        },
                        (error) => {
                            console.log('Error in user log : ' + error);
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
                    .get<any[]>('http://localhost:9000/api/v1/logout')
                    .subscribe(
                        (Response) => {
                            console.log('user logged out successfully');
                            this.me = null;
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
