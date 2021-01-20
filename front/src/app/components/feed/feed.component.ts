import {Component, EventEmitter, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {CreatePostComponent} from './create-post/create-post.component';
import {PostsService} from '../../services/posts.service';
import {ThemeService} from '../../services/theme.service';
import {ToastrService} from 'ngx-toastr';
import {Post} from '../../models/Post.model';
import {AuthService} from '../../services/auth.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';



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

    public isCollapsed = false;

    public posts: Array<Post> = null;

    public displayFilterForm = false;
    public hideFilterForm = true;
    public displaySwitcherForm = false;

    public currentPage = 1;

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
            page: [1, Validators.min(1)],
            nbPosts: [15, Validators.min(1)]
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

    toggleDisplayFilterForm(): void {
        if (!this.displayFilterForm) {
            this.hideFilterForm = false;
            setTimeout(() => this.displayFilterForm = !this.displayFilterForm, 100);
        } else {
            setTimeout(() => this.hideFilterForm = true, 1000);
            this.displayFilterForm = !this.displayFilterForm;
        }

    }

    toggleDisplaySwitcherForm(): void {
        setTimeout(() => scrollTo(0, 10000));
        this.displaySwitcherForm = !this.displaySwitcherForm;
    }

    previousPage(): void {
        const newPage = this.sortForm.value.page - 1;

        if (newPage > 0) {
            this.displaySwitcherForm = false;
            this.currentPage = newPage;

            this.sortForm.value.page = newPage;
            this.updatePostsForCurrentTheme();
            setTimeout(() => scrollTo(0, 0));
        }
    }

    nextPage(): void {
        this.displaySwitcherForm = false;
        const newPage = this.sortForm.value.page + 1;
        this.currentPage = newPage;
        this.sortForm.value.page = newPage;
        this.updatePostsForCurrentTheme();
        setTimeout(() => scrollTo(0, 0));
    }
}
