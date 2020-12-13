import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

    title: String = '';
    isSubmitting = false;
    authForm: FormGroup;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private formBuilder: FormBuilder
    ) {

    }

    ngOnInit() {
        // use FormBuilder to create a form group
        this.authForm = this.formBuilder.group({
            'email': ['', Validators.required],
            'password': ['', Validators.required]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.authForm.controls; }

    onSubmit(){

    }

}
