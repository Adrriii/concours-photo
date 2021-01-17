import {HttpClient, HttpEvent} from '@angular/common/http';
import { Injectable } from '@angular/core';
// import { resolve } from 'dns';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { User } from '../models/User.model';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private httpClient: HttpClient) { }

    getById(id: number): Observable<User> {
        return this.httpClient.get<User>(environment.apiBaseUrl + `user/${id}`);
    }

    getMe(): Observable<User> {
        return this.httpClient.get<User>(environment.apiBaseUrl + 'user/me');
    }

    update(user: User): Observable<User> {
        return this.httpClient.put<User>(environment.apiBaseUrl + 'user/me', user);
    }

}
