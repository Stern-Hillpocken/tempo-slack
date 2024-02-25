import { Component, OnInit } from '@angular/core';


import { Message } from 'src/app/core/models/message';

import { ServerService } from '../../services/server.service';
import { PseudoPassword } from 'src/app/core/models/pseudo-password.model';
import { Room } from 'src/app/core/models/room.model';
import { Server } from 'src/app/core/models/server.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { ServerSharedService } from 'src/app/shared/server-shared.service';
import { FormBuilder, FormGroup } from '@angular/forms';

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
  idMessage!:number;
  user!:PseudoPassword;
  activatedRoute: any;
  room!: Room;
  server!: Server;
  formUpdateMessage!: FormGroup;
  
 

  constructor(
    private serverService: ServerService,
    private localStorageService : LocalStorageService,
    private serverSharedService : ServerSharedService,
    private fb : FormBuilder){}


     ngOnInit() : void {

      this.serverSharedService.getServerShared().subscribe(serverInfo => {
        this.updateDisplay(serverInfo.currentServerId, serverInfo.currentRoomId)
        })
         
         this.formUpdateMessage = this.fb.group({
          content: [''] // Initialiser avec une chaîne vide
        });
    
        }
        updateDisplay(serverId : number, roomId : number): void{ 
          this.serverService.getRoomInServerById(serverId, roomId).subscribe(room =>{
            this.room=room;
            this.messagesList = room.messageList;
            this.idRoom=roomId;
            this.idServer= serverId;
            console.log(this.messagesList);
            console.log(roomId)
            console.log(serverId)
            console.log(room.title) 
         } 
          )
        }
        
    // getRoomName() {
    // // this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
    // // this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
    
    // // this.serverService.getRoomInServerById(this.idServer, this.idRoom).subscribe(room => {
    // //   this.room.title;
    // //   console.log(this.room.title)})
    //   this.serverSharedService.getServerShared().subscribe(serverInfo => {
    //     this.serverService.getRoomInServerById(serverInfo.currentServerId, serverInfo.currentRoomId).subscribe(room => 
    //       console.log(this.room.title) )
    // })
  
    onAddMessageReceive(message: string){
      console.log(message)
    this.user = this.localStorageService.getPseudoPassword();
    let dto= {user : this.user, content : message} ;
    this.serverSharedService.getServerShared().subscribe(serverInfo => {
    this.serverService.addMessage(dto, serverInfo.currentServerId, serverInfo.currentRoomId).subscribe(v =>{
      console.log(v);
      this.updateDisplay(this.idServer, this.idRoom)
    })
    })
  }
    
     
  onDeleteMessageReceive(idMessage: number){
  this.user = this.localStorageService.getPseudoPassword();
  console.log(this.user)
  this.serverSharedService.getServerShared().subscribe(serverInfo => {
  this.serverService.deleteMessageInRoomInServerById(serverInfo.currentServerId, serverInfo.currentRoomId, idMessage, this.user).subscribe(v => {
    console.log(v);
  this.updateDisplay(this.idServer, this.idRoom);
  }
 ) })
  }

   onUpdateMessageReceive(message:Message){
    this.idMessage = Number(this.message.id);
    this.user = this.localStorageService.getPseudoPassword();
    let dto = { user : this.user, content : message.content}
   this.serverService.updateMessage(this.idMessage, dto).subscribe(v =>{
       console.log(v);
     this.updateDisplay(this.idServer, this.idRoom)});
   }
      
 }


  

   
  
   
  

  
