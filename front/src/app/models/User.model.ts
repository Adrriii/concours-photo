import { UserSetting } from './UserSetting.model';

export class User {

    constructor(
        public id: number,
        public username: string,
        public userLevel: number,
        public victories: number,
        public score: number,
        public theme_score: number,
        public participations: number,
        public theme_participations: number,
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
            userJson.theme_score,
            userJson.participations,
            userJson.theme_participations,
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

    public isAttributeSettingAvailable(settingName: string): boolean {
        return this.settings[settingName] !== undefined;
    }

    public setSetting(settingName: string, value: string): void{
        this.settings[settingName].value = value;
    }
}
