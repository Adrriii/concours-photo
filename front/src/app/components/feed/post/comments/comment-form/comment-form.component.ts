import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {CommentsService} from '../../../../../services/comments.service';
import {Comment} from '../../../../../models/Comment.model';
import {Post} from '../../../../../models/Post.model';
import {User} from '../../../../../models/User.model';

@Component({
    selector: 'app-comment-form',
    templateUrl: './comment-form.component.html',
    styleUrls: ['./comment-form.component.css']
})
export class CommentFormComponent implements OnInit {

    @Output() commentAdded = new EventEmitter<Comment>();
    @Input() author: User;
    @Input() post: Post;

    form = this.formBuilder.group({
        comment: ['', Validators.required]
    });

    constructor(
        private formBuilder: FormBuilder,
        private commentService: CommentsService
    ) {
    }

    ngOnInit(): void {
    }

    onSubmit(): void {
        const comment = new Comment(
            User.fromJson(this.author),
            this.post,
            null,
            this.form.value.comment
        );

        this.commentService.postComment(this.post, comment).subscribe(
            newComment => this.commentAdded.emit(newComment)
        );
    }
}
