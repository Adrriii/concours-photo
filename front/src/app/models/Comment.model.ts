import {Post} from './Post.model';
import {User} from './User.model';

export class Comment {
    constructor(
        public author: User,
        public post: Post,
        public parent: Comment,
        public content: string,
        public id?: number
    ) {
    }
}
