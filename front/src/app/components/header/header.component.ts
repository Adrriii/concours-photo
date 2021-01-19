import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {User} from '../../models/User.model';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
    currentUserSubscription: Subscription;
    currentUser: User = null;
    photoUser: string = null;

    constructor(
        private authService: AuthService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.currentUserSubscription = this.authService.me.subscribe(
            user => {
                this.currentUser = user;
                if (user != null){
                    this.photoUser = user.photo;
                }
            }
        );
    }

    ngOnDestroy(): void {
        this.currentUserSubscription.unsubscribe();
    }

    isUserConnected(): boolean {
        return this.currentUser !== null;
    }

    logout(): void {
        this.authService.logOutUser().subscribe(
            () => this.router.navigate(['login']),
            error => console.log('Error while logging out : ' + error)
        );
    }
}
