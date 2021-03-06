import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/authentication/login/login.component';
import {HomeComponent} from './components/home/home.component';
import {RegisterComponent} from './components/authentication/register/register.component';
import {HeaderComponent} from './components/header/header.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {AuthGuardService} from './services/auth-guard.service';
import {AuthService} from './services/auth.service';
import {FeedComponent} from './components/feed/feed.component';
import {CreatePostComponent} from './components/feed/create-post/create-post.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {NgxFileDropModule} from 'ngx-file-drop';
import {ToastrModule} from 'ngx-toastr';
import {AuthInterceptorService} from './services/auth-interceptor.service';
import { ThemeComponent } from './components/theme/theme.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { EditSettingsComponent } from './components/user-settings/edit-settings/edit-settings.component';
import { PostComponent } from './components/feed/post/post.component';
import { CommentsComponent } from './components/feed/post/comments/comments.component';
import { CommentComponent } from './components/feed/post/comments/comment/comment.component';
import { CommentFormComponent } from './components/feed/post/comments/comment-form/comment-form.component';
import { ProfileComponent } from './components/profile/profile.component';
import { OtherProfileComponent } from './components/other-profile/other-profile.component';
import { UserService} from './services/user.service';
import { LeaderboardComponent } from './components/leaderboard/leaderboard.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatRadioModule } from '@angular/material/radio';
import { MatExpansionModule } from '@angular/material/expansion';

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HomeComponent,
        RegisterComponent,
        HeaderComponent,
        FeedComponent,
        CreatePostComponent,
        ThemeComponent,
        ProfileComponent,
        OtherProfileComponent,
        UserSettingsComponent,
        EditSettingsComponent,
        PostComponent,
        LeaderboardComponent,
        CommentsComponent,
        CommentComponent,
        CommentFormComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FontAwesomeModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        MatDialogModule,
        MatFormFieldModule,
        MatRadioModule,
        MatProgressBarModule,
        NgxFileDropModule,
        MatExpansionModule,
        ToastrModule.forRoot()
    ],
    providers: [
        AuthService,
        AuthGuardService,
        UserService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptorService,
            multi: true
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
