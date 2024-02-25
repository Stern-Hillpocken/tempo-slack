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
            } 
          )
        }
   onAddMessageReceive(message: string){
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
  this.serverSharedService.getServerShared().subscribe(serverInfo => {
  this.serverService.deleteMessageInRoomInServerById(serverInfo.currentServerId, serverInfo.currentRoomId, idMessage, this.user).subscribe(v => {
    console.log(v);
  this.updateDisplay(this.idServer, this.idRoom);
  }
 ) })
  }

   onUpdateMessageReceive(message: Message){
    if (message && message.id !== undefined) {
    this.idMessage = message.id   
    this.user = this.localStorageService.getPseudoPassword();
    let dto = { user : this.user, content : message.content}
   this.serverService.updateMessage(this.idMessage, dto).subscribe(v =>{
       console.log(v);
     this.updateDisplay(this.idServer, this.idRoom)});
   }
   }

   onUpdateNameRoomReceive(room : string){
    this.serverSharedService.getServerShared().subscribe(serverInfo => {
    this.serverService.updateRoom(serverInfo.currentServerId, serverInfo.currentRoomId, room).subscribe(v =>{
       console.log(v);
     this.updateDisplay(this.idServer, this.idRoom)});
   })
  }

   onDeleteRoomReceive(idRoom: number){
    this.user = this.localStorageService.getPseudoPassword();
    this.serverSharedService.getServerShared().subscribe(serverInfo => {
    this.serverService.deleteRoomInServerById(serverInfo.currentServerId, idRoom, this.user).subscribe(v => {
      console.log(v);
    }) 
  })
  }
}


  

   
  
   
  

  
