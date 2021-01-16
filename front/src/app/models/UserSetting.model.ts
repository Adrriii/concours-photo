export class UserSetting {
    constructor(
        public userId: number, 
        public isPublic: boolean,
        public setting: string,
        public value: string
    ){}
}