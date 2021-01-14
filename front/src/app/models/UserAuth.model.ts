import { User } from "./User.model";

export class UserAuth {

    public user : User;

    constructor(
        public username: string) {
    }
}
