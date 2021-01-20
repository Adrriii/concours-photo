import { Component, OnInit } from '@angular/core';
import {User} from '../../models/User.model';
import {AuthService} from '../../services/auth.service';
import {UserService} from '../../services/user.service';
import {Post} from '../../models/Post.model';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    public currentUser: User = null;
    public location: string = null;
    public bio: string = null;
    public listPosts: Array<Post> = null;
    public listImgs: Array<string> = null;
    public edited = false;
    public userRank: string;

    constructor(
        private authService: AuthService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
        this.currentUser = this.authService.currentUser;
        this.setBio();
        this.setLocation();
        this.setUserRank();
        this.userService.getPostsById(this.currentUser.id).subscribe(
            (userPosts) => {
                this.listPosts = userPosts;
                this.listImgs = new Array<string>();
                for (const post of userPosts){
                    this.listImgs.unshift(post.photo);
                }
            });
    }

    showAll(): void {
        if (this.edited === true) {
            this.edited = false;
            return;
        }
        this.edited = true;
    }

    setLocation(): void{
        if (this.authService.currentUser.isAttributeSettingAvailable('LOCATION')) {
            this.location = this.authService.currentUser.getSetting('LOCATION');
        } else {
            this.location = 'Unknown location';
        }
    }

    setBio(): void {
        if (this.authService.currentUser.isAttributeSettingAvailable('BIO')) {
            this.bio = this.authService.currentUser.getSetting('BIO');
        } else {
            this.bio = 'Currently no description provided';
        }
    }

    setUserRank(): void {
        if (this.authService.currentUser.rank === null){
            this.userRank = 'n/a';
        }else{
            this.userRank = String(this.authService.currentUser.rank);
        }
    }

}
