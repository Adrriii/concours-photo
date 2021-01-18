import {Component, Input, OnInit} from '@angular/core';
import {Post} from '../../../models/Post.model';
import {ReactionsService} from '../../../services/reactions.service';
import {AuthService} from '../../../services/auth.service';

@Component({
    selector: 'app-post',
    templateUrl: './post.component.html',
    styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
    @Input() post: Post;
    public isSelected = false;

    constructor(
        private reactionService: ReactionsService,
        private authService: AuthService
    ) {
    }

    ngOnInit(): void {
    }

    getCurrentClass(): string {
        return (this.isSelected) ? 'post-large-view' : 'post-medium-view';
    }

    toggleSelected(): void {
        this.isSelected = !this.isSelected;
    }


    sendLike(): void {
        if (! this.authService.isAuth) {
            return;
        }

        this.reactionService.postReaction(this.post.id, 'like').subscribe(
            () => {
                this.post.nbVote += 1;
                this.post.score += 1;
            }
        );
    }

    sendDislike(): void {
        if (! this.authService.isAuth) {
            return;
        }

        this.reactionService.postReaction(this.post.id, 'dislike').subscribe(
            () => {
                this.post.nbVote += 1;
                this.post.score -= 1;
            }
        );
    }

    getNumberLike(): number {
        return this.post.nbVote - (this.post.nbVote - this.post.score) / 2;
    }

    getNumberDislike(): number {
        return this.post.nbVote - this.getNumberLike();
    }
}
