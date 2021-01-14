import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {CreatePostComponent} from './create-post/create-post.component';
import {PostsService} from '../../services/posts.service';
import {ThemeService} from '../../services/theme.service';
import {Post} from '../../models/Post.model';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
    public currentThemeTitle: string;
    private currentThemeId: number;
    public listPosts: Array<Post>;
    public currentCommentSection: Array<Comment>;

  constructor(
      private toastr: ToastrService,
      private dialog: MatDialog,
      private postService: PostsService,
      private themeService: ThemeService,
  ) { }

  ngOnInit(): void {
      this.currentCommentSection = null;
      this.themeService.getCurrentTheme().subscribe((currentTheme) => {
          this.currentThemeId = currentTheme.id;
          this.currentThemeTitle = currentTheme.title;
          this.postService.getPostsByTheme(this.currentThemeId).subscribe((posts) => {
              this.listPosts = posts;
          }, error => {
              console.log('Error when getting current theme: ' + error.message);
          });
      }, error => {
          console.log('Error when getting posts for current theme: ' + error.message);
      });
  }

  openDialog(): void {

      const dialogConfig = new MatDialogConfig();

      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.backdropClass = 'backdropBackground';
      dialogConfig.width = '30%';

      const dialogRef = this.dialog.open(CreatePostComponent, dialogConfig);

      dialogRef.afterClosed().subscribe(
          data => console.log('Dialog output:', data)
      );
  }

    getCommentsPost(postId: number): void {
      this.postService.getAllCommentByPostId(postId).subscribe((comments) => {
        this.currentCommentSection = comments;
      }, error => { console.log('Error when getting posts for `${postId}`: ' + error.message);
      });
    }

    testGetPost(postId: number): void {
        this.postService.getById(postId).subscribe(
            post => console.log('Receive post: ' + post),
            error => console.log('Error while receiving post: ' + error.message)
        );
    }
}
