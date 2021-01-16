import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Post} from '../../../../models/Post.model';
import {Comment} from '../../../../models/Comment.model';
import {CommentsService} from '../../../../services/comments.service';
import {UserService} from '../../../../services/user.service';
import {User} from '../../../../models/User.model';
import {AuthService} from '../../../../services/auth.service';
import {Subscription} from 'rxjs';

@Component({
    selector: 'app-comments',
    templateUrl: './comments.component.html',
    styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit, OnDestroy {
    @Input() post: Post;
    public comments: Array<Comment> = null;

    public currentUser: User = null;
    private currentUserSubscription: Subscription;

    constructor(
        private commentService: CommentsService,
        private authService: AuthService
    ) {
    }

    ngOnInit(): void {
        this.commentService.getAllForPost(this.post).subscribe(
            comments => this.comments = comments,
            error => console.log('Error while loading comment for post : ' + this.post + ' -> ' + error.message)
        );

        this.currentUserSubscription = this.authService.me.subscribe(
            currentUser => this.currentUser = currentUser
        );
        this.authService.emitMe();
    }

    ngOnDestroy(): void {
        this.currentUserSubscription.unsubscribe();
    }

}
