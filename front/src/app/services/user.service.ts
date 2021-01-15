import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { User } from '../models/User.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  httpClient: HttpClient;

  constructor() { }

  getById(id: number): Observable<User> {
    return this.httpClient.get<User>(environment.apiBaseUrl + `user/${id}`);
  }

  getMe(): Observable<User> {
    return this.httpClient.get<User>(environment.apiBaseUrl + 'me');
  }

  update(): void {
    
  }
  
}
