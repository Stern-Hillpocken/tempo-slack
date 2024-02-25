import { Component, OnInit } from '@angular/core';


import { Message } from 'src/app/core/models/message';

import { ServerService } from '../../services/server.service';
import { PseudoPassword } from 'src/app/core/models/pseudo-password.model';
import { Room } from 'src/app/core/models/room.model';
import { Server } from 'src/app/core/models/server.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  messagesList: Message[] =[];
  message! : Message;
  idServer!: number;
  idRoom!: number;
  //idMessage!: number;
  user!:PseudoPassword;
  activatedRoute: any;
  room!: Room;
  server!: Server;
 
 

  constructor(
    private serverService: ServerService,
    private localStorageService : LocalStorageService){}


     ngOnInit() : void {

    //this.updateDisplay();

    //getMessageList(): void {
    this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
    this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
    this.serverService.getRoomInServerById(this.idServer, this.idRoom).subscribe(room =>
    this.messagesList = room.messageList)
    // this.updateDisplay();
    } 
        
    getRoomName() {
    this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
    this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
    
    this.serverService.getRoomInServerById(this.idServer, this.idRoom).subscribe(room => {
      this.room.title;
      console.log(this.room.title)})
    }
    onAddMessageReceive(message: string){
      console.log(message)
    this.user = this.localStorageService.getPseudoPassword();
    let dto= {user : this.user, content : message} ;
    this.serverService.addMessage(dto, this.idServer, this.idRoom).subscribe(v =>console.log(v))
    }
      
   } 
  
   
  

  
