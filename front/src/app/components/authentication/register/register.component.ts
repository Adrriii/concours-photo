import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import {AuthService} from '../../../services/auth.service';
import {sha256} from 'js-sha256';


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
            password: ['', Validators.required]
        });
    }

    get getForm(): any {
        return this.form.controls;
    }

    onSubmit(): void {
        this.submitted = true;

        // stop here if form is invalid
        if (this.form.invalid) {
            // Garder submitted a true ?
            console.log('Invalid form values !');
            return;
        }

        console.log(`try to register ${this.form.get('username').value} ${this.form.get('password').value}`);

        this.authService.createNewUser(
            this.form.get('username').value,
            this.form.get('password').value).then(
            () => this.router.navigate(['home'])
        ).catch(
            error => {
                console.log('Error in log : ' + error);
            }
        );

        // this.loading = true;
        // this.registerService.register(this.form.value)
        //     .pipe(first())
        //     .subscribe(
        //         data => {
        //             this.router.navigate(['../login'], { relativeTo: this.route });
        //         },
        //         error => {
        //             this.alertService.error(error);
        //             this.loading = false;
        //         });
    }

}
