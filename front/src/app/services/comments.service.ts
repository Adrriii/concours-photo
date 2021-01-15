import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import {Post} from '../models/Post.model';
import {Comment} from '../models/Comment.model';
import {environment} from '../../environments/environment.prod';

@Injectable({
    providedIn: 'root'
})
export class CommentsService {

    constructor(
        private httpClient: HttpClient
    ) {
    }

    public getAllForPost(post: Post): Observable<Array<Comment>> {
        return this.httpClient.get<Array<Comment>>(environment.apiBaseUrl + `posts/${post.id}/comments`);
    }
}
