import { UserSetting } from './UserSetting.model';

export class User {

    constructor(
        public id: number,
        public username: string,
        public userLevel: number,
        public victories: number,
        public score: number,
        public participations: number,
        public photo: string,
        public photoDelete: string,
        public settings: Map <string, UserSetting>
    ) {}

    static fromJson(userJson: User): User {
        return new User(
            userJson.id,
            userJson.username,
            userJson.userLevel,
            userJson.victories,
            userJson.score,
            userJson.participations,
            userJson.photo,
            userJson.photoDelete,
            userJson.settings
        );
    }
}
