import {Reactions} from './Reactions.model';
import {User} from './User.model';
import {Label} from './Label.model';
import {Theme} from './Theme.model';

export class Post {
    constructor(
        public title: string,
        public reacted: string,
        public reactions: Array<Reactions>,
        public author: User,
        public label: Label,
        public theme: Theme,
        public photo: string,
        public photoDelete: string,
        public id?: number,
    ) {
    }
}
