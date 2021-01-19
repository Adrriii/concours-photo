import { UserSetting } from './UserSetting.model';

export class User {

    constructor(
        public id: number,
        public username: string,
        public userLevel: number,
        public victories: number,
        public score: number,
        public participations: number,
        public rank: number,
        public photo: string,
        public photoDelete: string,
        public theme: number,
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
            userJson.rank,
            userJson.photo,
            userJson.photoDelete,
            userJson.theme,
            userJson.settings
        );
    }

    public getSetting(settingName: string): string{
        return this.settings[settingName].value;
    }

    public setSetting(settingName: string, value: string): void{
        this.settings[settingName].value = value;
    }
}
