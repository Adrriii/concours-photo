import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { sha256 } from 'js-sha256';
import { User } from '../models/User.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public me : User;

  constructor(private httpClient : HttpClient) { }

  createNewUser(username : string, password : string) {
    return new Promise(
      (resolve, reject) => {
        this.httpClient
          .post("http://localhost:9000/register", 
          {
            username : username,
            passwordHash : sha256(password)
          })
          .subscribe(
            () => {
              console.log("user created successfully");
            },
            (error) => {
              console.log("error");
            }
          );
      }
    );
  }

  logInNewUser(username : string, password : string) {
    return new Promise(
      (resolve, reject) => {
        this.httpClient
          .post("http://localhost:9000/login", 
          {
            username : username,
            passwordHash : sha256(password)
          })
          .subscribe(
            () => {
              console.log("user logged successfully");
            },
            (error) => {
              console.log("error");
            }
          );
      }
    );
  }

  logOutUser(username : string, password : string) {
    return new Promise(
      (resolve, reject) => {
        this.httpClient
          .get<any[]>("http://localhost:9000/logout")
          .subscribe(
            (Response) => {
              console.log("user unlogged successfully");
            },
            (error) => {
              console.log("error");
            }
          );
      }
    );
  }
}
