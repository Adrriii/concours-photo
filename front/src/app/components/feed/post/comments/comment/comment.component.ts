import {Component, Input, OnInit} from '@angular/core';
import {Comment} from '../../../../../models/Comment.model';
import {UserService} from '../../../../../services/user.service';
import {User} from '../../../../../models/User.model';

@Component({
    selector: 'app-comment',
    templateUrl: './comment.component.html',
    styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
    @Input() comment: Comment;
    @Input() currentUser: User;

    constructor(

    ) {
    }

    ngOnInit(): void {
    }

    getUserClass(): string {
        if (this.currentUser !== null && this.currentUser.username === this.comment.author.username) {
            return 'author-comment';
        }

        return 'user-comment';
    }
}
