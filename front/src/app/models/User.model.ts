export class User {
    public settings : Map <string, string>;

    constructor(
        public id: number,
        public username: string,
        public userLevel: number,
        public victories: number,
        public score: number
    ) {}

    static fromJson(userJson: User): User {
        return new User(
            userJson.id,
            userJson.username,
            userJson.userLevel,
            userJson.victories,
            userJson.score
        );
    }

    public compare(user : User) : boolean {
        this.settings.forEach((value, key) => {
            let setting : string = user.settings.get(key);
            if(setting !== value){
                console.log(`error while comparing user's settings : ${setting} not equals ${value}`);
                return false;
            }    
        });

        return this.id === user.id
            && this.username === user.username
            && this.userLevel === user.userLevel
            && this.victories === user.victories
            && this.score === user.score;
    }
}
