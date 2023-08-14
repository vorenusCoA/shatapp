 import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { ShatappApiService } from '../shatapp-api.service';
import { RxStompService } from '../rx-stomp.service';
import { ActivatedRoute, Router } from '@angular/router';
import { concatMap, map, tap } from 'rxjs';
import { User } from '../model/user';
import { ChatMessage } from '../model/chatMessage';
import { MessageType } from '../model/messageType';

@Component({
  selector: 'app-chat-room',
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.css']
})
export class ChatRoomComponent  {
}
  /*
  // @ts-ignore
  currentUser: User
  loggedUsers: Map<string, User> = new Map<string, User>()
  receivedMessages: any[] = [];
  chatRoomId: string = ""
  // @ts-ignore, to suppress warning related to being undefined
  private subscription: Subscription;
  message: string = ""

  constructor(private auth: AuthService, private shatappApi: ShatappApiService, private rxStompService: RxStompService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {

    this.chatRoomId = this.route.snapshot.paramMap.get("id")!;

    this.subscription = this.auth.user$
      .pipe(
        tap((userProfile) => {
          if (userProfile) {
            const user: User = { email: userProfile.email!, name: userProfile?.name, picture: userProfile?.picture }
            this.currentUser = user;
          }
        }),
        concatMap(() => this.shatappApi.getLoggedUsers(this.chatRoomId)),
        map((users) => users.filter((user: User) => user.email !== this.currentUser.email)),
        tap((users) => {
          for (let user of users) {
            this.loggedUsers.set(user.email, user)
          }
        }),
        concatMap(() => this.rxStompService.watch({ destination: '/topic/' + this.chatRoomId, subHeaders: { id: this.currentUser.email || "" } }))
      )
      .subscribe((message) => {
        const chatMessage: ChatMessage = JSON.parse(message.body)
        const user: User = { name: chatMessage.sender.name, picture: chatMessage.sender.picture, email: chatMessage.sender.email! }
        this.receivedMessages.push(this.getFormattedMessage(chatMessage))
        if (chatMessage.type === MessageType.JOIN && chatMessage.sender.email !== this.currentUser.email) {
          this.loggedUsers.set(user.email, user)
        } else if (chatMessage.type === MessageType.LEAVE) {
          this.loggedUsers.delete(user.email)
        }
      });

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  getFormattedMessage(chatMessage: ChatMessage): any {
    const content = chatMessage.content
    const align = chatMessage.type === MessageType.JOIN || chatMessage.type === MessageType.LEAVE ? 'center' : (chatMessage.sender.email === this.currentUser.email ? 'right' : 'left')
    const header = (align === 'left' ? chatMessage.sender.name : "")

    return { content: content, align: align, header: header }
  }

  logout() {
    this.auth.logout({ logoutParams: { returnTo: document.location.origin + '/welcome' } })
  }

  prepareMessage() {

    if (!this.message)
      return;


    this.sendMessage();
  }

  sendMessage() {
    const chatMessage: ChatMessage = {
      content: this.message,
      sender: {
        email: this.currentUser.email,
        name: this.currentUser.name || "",
        picture: "",
      },

      receiverEmail: "",
      type: MessageType.CHAT
    }
    this.rxStompService.publish({ destination: '/app/send', body: JSON.stringify(chatMessage) });
    this.message = ""
  }

}

 */