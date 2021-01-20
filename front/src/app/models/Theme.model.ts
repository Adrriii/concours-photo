import {User} from './User.model';

export class Theme {
    constructor(
        public id: number,
        public title: string,
        public photo: string,
        public state: string,
        public date: string,
        public winner: User,
        public author: User,
        public nbVotes: number
    ) {
    }
}
