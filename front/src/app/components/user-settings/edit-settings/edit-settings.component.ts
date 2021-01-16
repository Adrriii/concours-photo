import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CreatePostComponent } from '../../feed/create-post/create-post.component';

@Component({
  selector: 'app-edit-settings',
  templateUrl: './edit-settings.component.html',
  styleUrls: ['./edit-settings.component.css']
})
export class EditSettingsComponent implements OnInit {
  [x: string]: any;

  constructor(
    private dialogRef: MatDialogRef<CreatePostComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) { }

  ngOnInit(): void {
    console.log("nrbrnrbbjhbqjh")
  }

  update(): void {
    this.dialogRef.close();
  }

  close(): void {
    this.dialogRef.close();
  }

}
