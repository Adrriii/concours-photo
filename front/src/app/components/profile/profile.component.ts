import { Component, OnInit } from '@angular/core';
import {User} from '../../models/User.model';
import {AuthService} from '../../services/auth.service';
import {UserService} from "../../services/user.service";
import {Post} from "../../models/Post.model";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    public currentUser: User = null;
    public listPosts: Array<Post> = null;
    public listImgs: Array<string> = null;
    public edited = false;

    constructor(
        private authService: AuthService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
        this.userService.getMe().subscribe(user => {
            this.currentUser = user;
            this.userService.getPostsById(user.id).subscribe(
                (userPosts) => {
                    this.listPosts = userPosts;
                    this.listImgs = new Array<string>();
                    for (const post of userPosts){
                        this.listImgs.unshift(post.photo);
                    }
                }
            );
        });
    }

    showAll(): void {
        if (this.edited === true) {
            this.edited = false;
            return;
        }
        this.edited = true;
    }

}
