import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {Post} from '../models/Post.model';
import {environment} from '../../environments/environment.prod';

@Injectable({
    providedIn: 'root'
})
export class PostsService {

    constructor(
        private httpClient: HttpClient
    ) {
    }

    getAllCommentByPostId(postId: number): Observable<Comment[]> {
        return this.httpClient.get<Comment[]>(environment.apiBaseUrl + `feed/${postId}/comments`);
    }

    getPostsByTheme(themeId: number): Observable<Post[]> {
        return this.httpClient.get<Post[]>(environment.apiBaseUrl + `posts/theme/${themeId}`);
    }

    post(data: FormData): Observable<Post> {
        return this.httpClient.post<Post>(environment.apiBaseUrl + 'posts/', data);
    }
}
