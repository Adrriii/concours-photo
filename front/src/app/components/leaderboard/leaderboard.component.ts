import { Component, OnInit } from '@angular/core';
import {User} from "../../models/User.model";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

    public listUsersLeaderboard: Array<User>;

    constructor(
        private userService: UserService
    ) {
    }

    ngOnInit(): void {
        this.userService.getUsersGlobalLeaderboard().subscribe((users) => {
            this.listUsersLeaderboard = users;
        });
    }

}
