import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {CommentsService} from '../../../../../services/comments.service';

@Component({
    selector: 'app-comment-form',
    templateUrl: './comment-form.component.html',
    styleUrls: ['./comment-form.component.css']
})
export class CommentFormComponent implements OnInit {

    form = this.formBuilder.group({
        comment: ''
    });

    constructor(
        private formBuilder: FormBuilder,
        private commentService: CommentsService
    ) {
    }

    ngOnInit(): void {
    }

    onSubmit(): void {
        console.log('Hop submit comment (TODO)');
    }
}
