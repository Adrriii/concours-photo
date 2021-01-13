import {Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry} from 'ngx-file-drop';
import {ToastrService} from 'ngx-toastr';

// TODO à delete quand il y aura le service
import {HttpClient} from '@angular/common/http';
import {PostsService} from '../../../services/posts.service';
import {Post} from '../../../models/Post.model';

@Component({
    selector: 'app-create-post',
    templateUrl: './create-post.component.html',
    styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
    form: FormGroup;
    files: NgxFileDropEntry[] = [];
    imagePreview: string | ArrayBuffer = null;
    currentFile: NgxFileDropEntry;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: MatDialogRef<CreatePostComponent>,
        @Inject(MAT_DIALOG_DATA) data,
        private toastr: ToastrService,
        private postService: PostsService
    ) {
    }

    public dropped(files: NgxFileDropEntry[]): void {
        this.files = files;

        // Only one file required on create post
        if (this.files.length > 1) {
            this.files.pop();
        }

        const droppedFile = files[0];
        // Is it a file ?
        if (droppedFile.fileEntry.isFile && this.isFileAllowed(droppedFile.fileEntry.name)) {
            this.toastr.success('File successfully dropped.');

            this.currentFile = droppedFile;

            const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
            const reader = new FileReader();
            fileEntry.file((file: File) => {
                reader.readAsDataURL(file);
                reader.onload = () => {
                    this.imagePreview = reader.result;
                };

                // Access the real file
                console.log(droppedFile.relativePath, file);
            });
        } else {
            this.toastr.error('Only files in ".jpg", ".jpeg", ".png" format are accepted and directories are not allowed.');
            this.imagePreview = null;
            this.files.pop();
        }
    }

    public fileOver(event): void {
        console.log(event);
    }

    public fileLeave(event): void {
        console.log(event);
    }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            title: ['', Validators.required]
        });
    }

    save(): void {
        if (this.files.length === 1) {
            const fileEntry = this.currentFile.fileEntry as FileSystemFileEntry;
            fileEntry.file((file: File) => {
                const formData = new FormData();
                const post = new Post(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                );

                formData.append('file', file, this.currentFile.relativePath);

                this.postService.sendPost(
                    formData
                ).subscribe(
                    postSent => {
                        console.log('Receive post -> ' + postSent);
                        this.toastr.success('You posted your picture !');
                    },
                    error => {
                        console.log('Error while sending file -> ' + error);
                        this.toastr.error(error.message);
                    }
                );
            });
        }

        this.dialogRef.close(this.form.value);
    }

    close(): void {
        this.dialogRef.close();
    }

    isFileAllowed(fileName: string): boolean {
        let isFileAllowed = false;
        const allowedFiles = ['.jpg', '.jpeg', '.png'];
        const regex = /(?:\.([^.]+))?$/;
        const extension = regex.exec(fileName);
        if (extension !== undefined && extension !== null) {
            for (const ext of allowedFiles) {
                if (ext === extension[0].toLowerCase()) {
                    isFileAllowed = true;
                }
            }
        }
        return isFileAllowed;
    }

    isFileSelected(): boolean {
        return this.imagePreview !== null;
    }
}
