import {Component, EventEmitter, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {CreatePostComponent} from './create-post/create-post.component';
import {PostsService} from '../../services/posts.service';
import {ThemeService} from '../../services/theme.service';
import {ToastrService} from 'ngx-toastr';
import {Post} from '../../models/Post.model';
import {AuthService} from '../../services/auth.service';
import { FormBuilder, FormGroup } from '@angular/forms';


@Component({
    selector: 'app-feed',
    templateUrl: './feed.component.html',
    styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
    public currentThemeTitle: string;
    private currentThemeId: number;
    public currentCommentSection: Array<Comment>;
    sortForm: FormGroup;

    public posts: Array<Post> = null;

    constructor(
        private toastr: ToastrService,
        private dialog: MatDialog,
        private postService: PostsService,
        private themeService: ThemeService,
        private authService: AuthService,
        private formBuilder: FormBuilder
    ) {
    }

    ngOnInit(): void {
        this.currentCommentSection = null;

        this.themeService.getCurrentTheme().subscribe(
            currentTheme => {
                this.currentThemeId = currentTheme.id;
                this.currentThemeTitle = currentTheme.title;

                this.updatePostsForCurrentTheme();
            }, error => {
                console.log('Error when getting posts for current theme: ' + error.message);
            }
        );

        this.sortForm = this.formBuilder.group({
            direction: 'DESC',
            sort: 'score',
            labels: '',
            page: 1,
            nbPosts: 15
        });
    }

    updatePostsForCurrentTheme(): void {
        this.postService.getPostsByTheme(this.currentThemeId, this.sortForm.value).subscribe(
            posts => {
                this.posts = posts.map(p => Post.fromJson(p));
            }, error => {
                console.log('Error when getting posts by theme: ' + error.message);
            }
        );
    }

    openDialog(): void {

        const dialogConfig = new MatDialogConfig();

        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = true;
        dialogConfig.backdropClass = 'backdropBackground';
        dialogConfig.width = '30%';

        const dialogRef = this.dialog.open(CreatePostComponent, dialogConfig);

        (dialogRef.componentInstance.postAdded as EventEmitter<void>).subscribe(() => {
            this.updatePostsForCurrentTheme();
        });

        dialogRef.afterClosed().subscribe(
            data => {}
        );
    }

    isUserLoggedIn(): boolean {
        return this.authService.isAuth;
    }

    onSubmitForm(): void {
        this.updatePostsForCurrentTheme();
    }
}
