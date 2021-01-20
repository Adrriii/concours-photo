import { Component, OnInit } from '@angular/core';
import {User} from '../../models/User.model';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

    public listUsersGlobalLeaderboard: Array<User>;
    public listUsersCurrentLeaderboard: Array<User>;

    constructor(
        private userService: UserService
    ) {
    }

    ngOnInit(): void {
        this.userService.getUsersGlobalLeaderboard().subscribe((users) => {
            this.listUsersGlobalLeaderboard = users;
        });
        this.userService.getUsersCurrentLeaderboard().subscribe((users) => {
            this.listUsersCurrentLeaderboard = users;
        });
    }

}
