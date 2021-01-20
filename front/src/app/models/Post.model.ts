import {Reactions} from './Reactions.model';
import {User} from './User.model';
import {Label} from './Label.model';
import {Theme} from './Theme.model';

export class Post {
    constructor(
        public title: string,
        public date: string,
        public score: number,
        public nbVote: number,
        public nbComment: number,
        public reacted: string,
        public reactions: Array<Reactions>,
        public author: User,
        public label: Label,
        public theme: Theme,
        public photo: string,
        public photoDelete: string,
        public reactionsUser: any,
        public id?: number,
    ) {
    }

    static fromJson(json: Post): Post {
        return new Post(
            json.title,
            json.date,
            json.score,
            json.nbVote,
            json.nbComment,
            json.reacted,
            json.reactions,
            User.fromJson(json.author),
            json.label,
            json.theme,
            json.photo,
            json.photoDelete,
            json.reactionsUser,
            json.id
        );
    }
}
