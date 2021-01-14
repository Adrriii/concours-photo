export class User {
    constructor(
        public id: number,
        public username: string,
        public userLevel: number,
        public victories: number,
        public score: number
    ) {}
}
