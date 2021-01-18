import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ReactionsService {

    constructor(
        private httpClient: HttpClient
    ) {
    }

    public postReaction(postId: number, reaction: string): Observable<any> {
        return this.httpClient.post(`${environment.apiBaseUrl}posts/${postId}/react`, reaction);
    }

    public putReaction(postId: number, reaction: string): Observable<any> {
        return this.httpClient.put(`${environment.apiBaseUrl}posts/${postId}/react`, reaction);
    }

    public deleteReaction(postId: number): Observable<any> {
        return this.httpClient.delete(`${environment.apiBaseUrl}posts/${postId}/react`);
    }
}
