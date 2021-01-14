export class User {
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
}
