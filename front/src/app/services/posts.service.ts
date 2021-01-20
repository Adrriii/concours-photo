import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {Post} from '../models/Post.model';
import {environment} from '../../environments/environment.prod';
import { FeedFilter } from '../models/FeedFilter.model';

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

    getPostsByTheme(themeId: number, filter: FeedFilter): Observable<Post[]> {
        let themeQuery = "";
        
        if(themeId !== 0) {
            themeQuery = `theme=${themeId}&`;
        }
        
        return this.httpClient.get<Post[]>(environment.apiBaseUrl 
            + `feed?`
            + themeQuery
            + `direction=${filter.direction}&sort=${filter.sort}&labels=${filter.labels}&page=${filter.page}&nbPosts=${filter.nbPosts}`);
    }

    post(data: FormData): Observable<Post> {
        return this.httpClient.post<Post>(environment.apiBaseUrl + 'posts/', data);
    }
}
