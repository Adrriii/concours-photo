import {Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialog, MatDialogConfig} from '@angular/material/dialog';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
    form: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: MatDialogRef<CreatePostComponent>,
        @Inject(MAT_DIALOG_DATA) data
    ) { }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            title: ['', Validators.required]
        });
    }

    save(): void {
        this.dialogRef.close(this.form.value);
    }

    close(): void {
        this.dialogRef.close();
    }

}
