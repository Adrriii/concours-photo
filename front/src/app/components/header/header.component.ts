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

    constructor(
        private authService: AuthService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.currentUserSubscription = this.authService.me.subscribe(
            user => this.currentUser = user
        );
    }


    ngOnDestroy(): void {
        this.currentUserSubscription.unsubscribe();
    }

    isUserConnected(): boolean {
        return this.currentUser !== null;
    }

    logout(): void {
        this.authService.logOutUser()
            .then(() => {
                console.log('Logout successfully');
                this.router.navigate(['login']);
            })
            .catch(error => console.log('Error while logging out : ' + error));
    }
}
