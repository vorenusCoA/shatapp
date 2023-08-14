import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  isLoginBtnDisabled: boolean = false
  showLogin: boolean = false

  constructor(private auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {

    this.auth.isAuthenticated$.subscribe(isAuthenticated => {
      if (isAuthenticated) {
        this.router.navigate(['/main'])
      } else {
        this.showLogin = true
      }
    })

  }

  login() {
    this.isLoginBtnDisabled = true
    this.auth.loginWithRedirect()
  }

}
