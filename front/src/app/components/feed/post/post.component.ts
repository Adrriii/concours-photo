import {Component, Input, OnInit} from '@angular/core';
import {Post} from '../../../models/Post.model';
import {Router} from '@angular/router';

@Component({
    selector: 'app-post',
    templateUrl: './post.component.html',
    styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
    @Input() post: Post;
    public isSelected = false;

    constructor(private router: Router) {
    }

    ngOnInit(): void {
    }

    getCurrentClass(): string {
        return (this.isSelected) ? 'post-large-view' : 'post-medium-view';
    }

    toggleSelected(): void {
        this.isSelected = !this.isSelected;
    }
}
