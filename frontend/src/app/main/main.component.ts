import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { ShatappApiService } from '../shatapp-api.service';
import { RxStompService } from '../rx-stomp.service';
import { Subscription, concatMap, map, share, tap } from 'rxjs';
import { User } from '../model/user';
import { InviteMessage } from '../model/inviteMessage';
import { MatDialog } from '@angular/material/dialog';
import { InviteFriendDialogComponent } from '../invite-friend-dialog/invite-friend-dialog.component';
import { ChatMessage } from '../model/chatMessage';
import { InviteStatus } from '../model/inviteStatus';
import { MessageType } from '../model/messageType';
import { MessageDTO } from '../model/messageDTO';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, OnDestroy {

  // @ts-ignore
  currentUser: User
  // @ts-ignore
  private inviteSubscription: Subscription
  // @ts-ignore
  private messageSubscription: Subscription
  friends: Set<User> = new Set
  badgeContent: any
  invites: Set<InviteMessage> = new Set
  message: string = ""
  conversations: Map<string, MessageDTO[]> = new Map(); // name of the chat : messages
  activeChat: string = "NONE";
  currentConversationMessages: MessageDTO[] = []

  constructor(private auth: AuthService, private shatappApiService: ShatappApiService, public dialog: MatDialog, private rxStompService: RxStompService) {
  }

  ngOnInit(): void {

    const authUserSubscription = this.auth.user$.
      pipe(
        tap(userProfile => {
          if (userProfile) {
            const user: User = { email: userProfile.email!, name: userProfile?.name, picture: userProfile?.picture }
            this.currentUser = user;
          }
        }),
        map(userProfile => userProfile?.email),
        share()
      )

    this.inviteSubscription = authUserSubscription.pipe(
      concatMap((userEmail) => this.rxStompService.watch('/user/' + userEmail + "/invites"))
    ).subscribe(message => {
      const inviteMessage: InviteMessage = JSON.parse(message.body)
      console.log("You have received an INVITE: ")
      console.log(JSON.stringify(inviteMessage))
      if (inviteMessage.status === InviteStatus.PENDING) {
        this.invites.add(inviteMessage)
        this.badgeContent = 1
      } else if (inviteMessage.status === InviteStatus.ACCEPTED) {
        this.friends.add({ email: inviteMessage.to, picture: inviteMessage.toPicture })
      }

    })

    this.messageSubscription = authUserSubscription.pipe(
      concatMap((userEmail) => this.rxStompService.watch('/user/' + userEmail + "/messages"))
    ).subscribe(message => {
      const chatMessage: MessageDTO = JSON.parse(message.body)
      console.log("You have received a private MESSAGE: ")
      console.log(JSON.stringify(chatMessage))

      if (chatMessage.from === this.activeChat) {
        this.currentConversationMessages.push(chatMessage)
      } else {
        let messages = this.conversations.get(chatMessage.from)
        if (!messages) {
          messages = []
        }

        messages.push(chatMessage)
        this.conversations.set(chatMessage.from, messages)

      }

    })

    this.getFriends()
    this.getPendingFriendshipInvitesReceived()
    this.getMessagesOrderByDate()
  }

  ngOnDestroy() {
    this.inviteSubscription.unsubscribe();
    this.messageSubscription.unsubscribe();
  }

  logout() {
    this.auth.logout({ logoutParams: { returnTo: document.location.origin + '/welcome' } })
  }

  openInviteFriendDialog() {
    this.dialog.open(InviteFriendDialogComponent, {
      width: '350px',
    });
  }

  getFriends() {
    this.shatappApiService.getFriends().subscribe(friends => {
      for (let friend of friends) {
        this.friends.add(friend)
      }

    })
  }

  acceptInvite($event: any, invite: InviteMessage) {
    $event.stopPropagation()
    this.respondInvite(InviteStatus.ACCEPTED, invite)
    this.friends.add({ email: invite.from, picture: invite.fromPicture })
  }

  rejectInvite($event: any, invite: InviteMessage) {
    $event.stopPropagation()
    this.respondInvite(InviteStatus.REJECTED, invite)
  }

  private respondInvite(status: InviteStatus, invite: InviteMessage) {
    this.invites.delete(invite)
    invite.status = status
    this.shatappApiService.respondInvite(invite).subscribe()
  }

  getPendingFriendshipInvitesReceived() {
    this.shatappApiService.getPendingFriendshipInvitesReceived()
      .subscribe((pendingInvites: InviteMessage[]) => {
        if (pendingInvites.length !== 0) {
          for (let invite of pendingInvites) {
            this.invites.add(invite)
          }
        }
      })
  }

  getMessagesOrderByDate() {
    this.shatappApiService.getMessagesOrderByDate().subscribe((messages: MessageDTO[]) => {
      for (let message of messages) {

        let key: string
        let messagesInConversation: MessageDTO[] | undefined

        if (message.from === this.currentUser.email) {
          key = message.to
        } else {
          key = message.from
        }

        messagesInConversation = this.conversations.get(key)
        if (!messagesInConversation) {
          messagesInConversation = []
        }
        messagesInConversation.push(message)
        this.conversations.set(key, messagesInConversation)

      }
    })
  }

  prepareMessage() {

    if (!this.message)
      return;


    this.sendMessage();
  }

  sendMessage() {
    const messageDTO: MessageDTO = {
      content: this.message,
      from: this.currentUser.email,
      to: this.activeChat,
      sentDateTime: new Date()
    }
    this.rxStompService.publish({ destination: '/app/private-messages', body: JSON.stringify(messageDTO) });
    this.currentConversationMessages.push(messageDTO)
    this.message = ""
  }

  loadMessages(chatKey: string) {

    this.conversations.set(this.activeChat, this.currentConversationMessages)
    this.activeChat = chatKey
    this.currentConversationMessages = this.conversations.get(this.activeChat) || []

  }

}
