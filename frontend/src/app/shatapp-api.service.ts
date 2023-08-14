import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InviteMessage } from './model/inviteMessage';
import { InviteStatus } from './model/inviteStatus';
import { ChatMessage } from './model/chatMessage';
import { MessageDTO } from './model/messageDTO';

@Injectable({
  providedIn: 'root'
})
export class ShatappApiService {

  constructor(private http: HttpClient) { }

  createChatRoom(): Observable<any> {
    return this.http.post('http://localhost:8080/api/room', {})
  }

  findChatRoomById(id: string) {
    return this.http.get(`http://localhost:8080/api/room/${id}`)
  }

  getLoggedUsers(id: any): Observable<any> {
    return this.http.get(`http://localhost:8080/api/room/${id}/user`)
  }

  addFriend(friendEmail: string): Observable<any> {
    return this.http.post('http://localhost:8080/api/friendship-invite', friendEmail)
  }

  getFriends(): Observable<any> {
    return this.http.get(`http://localhost:8080/api/user/friend`)
  }

  getPendingFriendshipInvitesReceived(): Observable<InviteMessage[]> {
    return this.http.get<InviteMessage[]>('http://localhost:8080/api/friendship-invite')
  }

  respondInvite(inviteResponse: InviteMessage): Observable<any> {
    return this.http.put(`http://localhost:8080/api/friendship-invite`, inviteResponse)
  }

  getMessagesOrderByDate(): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>('http://localhost:8080/api/message')
  }
}
