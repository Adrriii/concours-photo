import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import {AuthService} from '../../../services/auth.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

    form: FormGroup;
    loading = false;
    submitted = false;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authService: AuthService
    ) { }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required],
            email: ['', Validators.required, Validators.email]
        });
    }

    onSubmit(): void {
        this.submitted = true;

        if (this.form.invalid) {
            return;
        }

        console.log(`try to register ${this.form.get('username').value} ${this.form.get('password').value}`);

        this.authService.createNewUser(
            this.form.get('username').value,
            this.form.get('password').value,
            this.form.get('email').value).subscribe(
            () => this.router.navigate(['login']),
            error => {
                console.log('Error while creating new user : ' + error);
            }
        );
    }
}
