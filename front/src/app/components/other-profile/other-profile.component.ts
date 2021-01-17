import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import {User} from '../../models/User.model';
import {Post} from '../../models/Post.model';

@Component({
  selector: 'app-other-profile',
  templateUrl: './other-profile.component.html',
  styleUrls: ['./other-profile.component.css']
})

export class OtherProfileComponent implements OnInit {
    private id: number;
    public UserQueried: User = null;
    public listPosts: Array<Post> = null;
    public listImgs: Array<string> = null;
    public edited = false;

    constructor(
        private route: ActivatedRoute,
        private userService: UserService
    ) {
    }

    ngOnInit(): void {
        const stringId = this.route.snapshot.paramMap.get('id');
        this.id = Number(stringId);
        this.userService.getById(this.id).subscribe((userQueried) => this.UserQueried = userQueried);
        this.userService.getPostsById(this.id).subscribe(
            (userPosts) => {
                this.listPosts = userPosts;
                this.listImgs = new Array<string>();
                for (const post of userPosts){
                    this.listImgs.push(post.photo);
                }
                this.listImgs.reverse();
            }
        );
    }

    showAll(): void {
        if (this.edited === true) {
            this.edited = false;
            return;
        }
        this.edited = true;
    }
}
