import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { HeaderComponent } from './header/header.component';
import {AppRoutingModule} from './app-routing.module';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HomeComponent,
        RegisterComponent,
        HeaderComponent
    ],
    imports: [
        BrowserModule,
        FontAwesomeModule,
        AppRoutingModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
