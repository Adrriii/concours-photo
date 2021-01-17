import { Component, OnInit } from '@angular/core';
import {User} from '../../models/User.model';
import {AuthService} from '../../services/auth.service';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    public currentUser: User = null;

    constructor(
        private authService: AuthService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
        // this.authService.me.subscribe(
        //      currentUser => this.currentUser = currentUser
        // );
        this.userService.getMe().subscribe(user => this.currentUser = user);
    }

}
