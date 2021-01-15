import {Component, Input, OnInit} from '@angular/core';
import {Post} from '../../../../models/Post.model';
import {Comment} from '../../../../models/Comment.model';
import {CommentsService} from '../../../../services/comments.service';

@Component({
    selector: 'app-comments',
    templateUrl: './comments.component.html',
    styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
    @Input() post: Post;
    public comments: Array<Comment> = null;

    constructor(
        private commentService: CommentsService
    ) {
    }

    ngOnInit(): void {
        this.commentService.getAllForPost(this.post).subscribe(
            comments => this.comments = comments,
            error => console.log('Error while loading comment for post : ' + this.post + ' -> ' + error.message)
        );
    }

}
