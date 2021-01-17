import { NgModule } from '@angular/core';
import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/authentication/login/login.component';
import {RegisterComponent} from './components/authentication/register/register.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {FeedComponent} from './components/feed/feed.component';
import {ThemeComponent} from './components/theme/theme.component';
import {ProfileComponent} from './components/profile/profile.component';
import {OtherProfileComponent} from './components/other-profile/other-profile.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { AuthGuardService } from './services/auth-guard.service';

const routes: Routes = [
    { path : 'feed', component: FeedComponent },
    { path : 'settings', canActivate: [AuthGuardService], component: UserSettingsComponent },
    { path : 'home', component: HomeComponent },
    { path : 'login', component: LoginComponent },
    { path : 'me', component: ProfileComponent },
    { path : 'register', component: RegisterComponent },
    { path : 'themes', component: ThemeComponent },
    { path : '', redirectTo: '/home', pathMatch: 'full'},
    { path : '**', component: PageNotFoundComponent }
];

const routerOptions: ExtraOptions = {
    anchorScrolling: 'enabled',
    scrollPositionRestoration: 'enabled',
};

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes, routerOptions)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
