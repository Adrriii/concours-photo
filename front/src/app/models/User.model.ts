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

    // public getSetting(settingName: string) : string {
    //     return this.settings.get(settingName).value;
    // }

    // public compare(user : User) : boolean {
    //     this.settings.forEach((value, key) => {
    //         let setting : string = user.settings.get(key);
    //         if(setting !== value){
    //             console.log(`error while comparing user's settings : ${setting} not equals ${value}`);
    //             return false;
    //         }
    //     });

    //     return this.id === user.id
    //         && this.username === user.username
    //         && this.userLevel === user.userLevel
    //         && this.victories === user.victories
    //         && this.score === user.score;
    // }
}
