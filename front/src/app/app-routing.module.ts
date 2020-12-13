import { NgModule } from '@angular/core';
import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';

const routes: Routes = [
    { path : 'home', component: HomeComponent },
    { path : 'login', component: LoginComponent },
    { path : 'register', component: RegisterComponent },
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
