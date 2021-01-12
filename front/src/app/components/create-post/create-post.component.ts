import {Component, Inject, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialog, MatDialogConfig} from '@angular/material/dialog';
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
    form: FormGroup;
    public files: NgxFileDropEntry[] = [];
    imagePreview: string | ArrayBuffer;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: MatDialogRef<CreatePostComponent>,
        @Inject(MAT_DIALOG_DATA) data,
        private toastr: ToastrService
    ) { }

    public dropped(files: NgxFileDropEntry[]): void {
        this.files = files;

        // Only one file required on create post
        if(this.files.length > 1){
            this.files.pop();
        }

        for (const droppedFile of files) {
            // Is it a file ?
            if (droppedFile.fileEntry.isFile && this.isFileAllowed(droppedFile.fileEntry.name)) {
                this.toastr.success('File successfully dropped.');

                const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
                const reader = new FileReader();
                fileEntry.file((file: File) => {
                    reader.readAsDataURL(file);
                    reader.onload = () => {
                        this.imagePreview = reader.result;
                    };

                    // Here you can access the real file
                    console.log(droppedFile.relativePath, file);

                    /**
                     // You could upload it like this:
                     const formData = new FormData()
                     formData.append('logo', file, relativePath)

                     // Headers
                     const headers = new HttpHeaders({
            'security-token': 'mytoken'
          })

                     this.http.post('https://mybackend.com/api/upload/sanitize-and-save-logo', formData, { headers: headers, responseType: 'blob' })
                     .subscribe(data => {
            // Sanitized logo returned from backend
          })
                     **/

                });
            } else {
                this.toastr.error('Only files in ".jpg", ".jpeg", ".png" format are accepted and directories are not allowed.');
                this.imagePreview = null;
                this.files.pop();
                // It was a directory (empty directories are added, otherwise only files)
                //const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
                //console.log(droppedFile.relativePath, fileEntry);
            }
        }
    }

    public fileOver(event): void{
        console.log(event);
    }

    public fileLeave(event): void{
        console.log(event);
    }

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

    isFileAllowed(fileName: string): boolean {
        let isFileAllowed = false;
        const allowedFiles = ['.jpg', '.jpeg', '.png'];
        const regex = /(?:\.([^.]+))?$/;
        const extension = regex.exec(fileName);
        if (undefined !== extension && extension !== null) {
            for (const ext of allowedFiles) {
                if (ext === extension[0].toLowerCase()) {
                    isFileAllowed = true;
                }
            }
        }
        return isFileAllowed;
    }
}
