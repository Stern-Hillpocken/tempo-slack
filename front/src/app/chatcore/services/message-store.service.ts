import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Message } from 'src/app/core/models/message';

@Injectable({
  providedIn: 'root'
})
export class MessageStoreService {

private readonly _messages : BehaviorSubject<Message[]> = new BehaviorSubject<Message[]>([]);//selt lecture, pas accessible en dehors de ctte classe
readonly messages$: Observable<Message[]>= this._messages.asObservable();
get messages(): Message[]{
  return this._messages.getValue();
}

set messages(val :Message[]){
  this._messages.next(val);
}

addMessage(newMessage : Message){
  this.messages=[...this.messages, newMessage]//ajoute nouveau client au tableau
}


  constructor() { }
}
