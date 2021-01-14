import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
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
        return this.httpClient.get<Comment[]>(environment.apiBaseUrl + `posts/${postId}/comments`);
    }

    getPostsByTheme(themeId: number): Observable<Post[]> {
        return this.httpClient.get<Post[]>(environment.apiBaseUrl + `posts/${themeId}`);
    }

    getById(id: number): Observable<Post> {
        return this.httpClient.get<Post>(environment.apiBaseUrl + `posts/${id}`);
    }

    post(data: FormData): Observable<Post> {
        return this.httpClient.post<Post>(environment.apiBaseUrl + 'posts/', data);
    }

    sendPost(data: FormData): Observable<Post> {
        return this.httpClient.post<Post>(environment.apiBaseUrl + 'posts/', data);
    }
}
