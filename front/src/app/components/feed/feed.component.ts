import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {CreatePostComponent} from './create-post/create-post.component';
import {PostsService} from '../../services/posts.service';
import {log} from 'util';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  constructor(
      private dialog: MatDialog,
      private postService: PostsService
  ) { }

  ngOnInit(): void {
  }

  openDialog(): void {

      const dialogConfig = new MatDialogConfig();

      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.backdropClass = 'backdropBackground';

      const dialogRef = this.dialog.open(CreatePostComponent, dialogConfig);

      dialogRef.afterClosed().subscribe(
          data => console.log('Dialog output:', data)
      );
  }

    testGetPost(postId: number): void {
        this.postService.getById(postId).subscribe(
            post => console.log('Receive post: ' + post),
            error => console.log('Error while receiving post: ' + error)
        );
    }
}
