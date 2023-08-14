import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { AuthModule, AuthHttpInterceptor } from '@auth0/auth0-angular';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDividerModule } from '@angular/material/divider';
import {MatInputModule} from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {MatDialogModule} from '@angular/material/dialog';
import { WelcomeComponent } from './welcome/welcome.component';
import { RxStompService } from './rx-stomp.service';
import { rxStompServiceFactory } from './rx-stomp-service-factory';
import { ChatRoomComponent } from './chat-room/chat-room.component';
import { FormsModule } from '@angular/forms';
import { MainComponent } from './main/main.component';
import { InviteFriendDialogComponent } from './invite-friend-dialog/invite-friend-dialog.component';
import {MatBadgeModule} from '@angular/material/badge';
import {MatMenuModule} from '@angular/material/menu';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    ChatRoomComponent,
    MainComponent,
    InviteFriendDialogComponent
  ],
  imports: [
    BrowserModule,
    // Import the module into the application, with configuration
    AuthModule.forRoot({
      domain: '',
      clientId: '',
      authorizationParams: {
        redirect_uri: window.location.origin + '/welcome',
        audience: 'http://localhost:8080/shatapp-api'
      },
      // The AuthHttpInterceptor configuration
      httpInterceptor: {
        allowedList: [

          // Using an absolute URI
          {
            uri: 'http://localhost:8080/api/*',
            tokenOptions: {
              authorizationParams: {
                audience: 'http://localhost:8080/shatapp-api'
              }
            },
          },
        ],
      },

    }),
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatDividerModule,
    MatSidenavModule,
    MatInputModule,
    MatDialogModule,
    MatBadgeModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthHttpInterceptor, multi: true },
    { provide: RxStompService, useFactory: rxStompServiceFactory }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
