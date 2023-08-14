import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ShatappApiService } from '../shatapp-api.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-invite-friend-dialog',
  templateUrl: './invite-friend-dialog.component.html',
  styleUrls: ['./invite-friend-dialog.component.css']
})
export class InviteFriendDialogComponent {

  email: string = ""
  errorMessage: string = ""

  constructor(private dialogRef: MatDialogRef<InviteFriendDialogComponent>, private router: Router, private shatappApiService: ShatappApiService) { }

  inviteFriend(form: NgForm) {

    if (form.invalid)
      return;

    this.shatappApiService.addFriend(this.email).subscribe(
      {
        next: (() => {
          this.dialogRef.close()
        }),
        error: (error => {
          if (error.status === 400) {
            this.errorMessage = 'User not found'
          } else if (error.status === 409) {
            this.errorMessage = 'Invite for that destination already exists'
          }

        })
      })
  }

  emptyErrorMessage() {
    console.log("Empty CALLED")
    this.errorMessage = ""
  }

}
