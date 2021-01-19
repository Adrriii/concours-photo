import {User} from './User.model';

export class UserPublic {
    constructor(
        public id,
        public username,
        public victories,
        public score,
        public participations,
        public rank,
        public photo
    ) {
    }

    static fromUser(user: User): UserPublic {
        return new UserPublic(
            user.id,
            user.username,
            user.victories,
            user.score,
            user.participations,
            user.rank,
            user.photo
        );
    }
}
