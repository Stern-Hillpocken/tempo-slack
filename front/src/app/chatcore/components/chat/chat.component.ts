import { Component, OnInit } from '@angular/core';
import { Message } from 'src/app/core/models/message';
import { ServerService } from '../../services/server.service';
import { PseudoPassword } from 'src/app/core/models/pseudo-password.model';
import { Room } from 'src/app/core/models/room.model';
import { Server } from 'src/app/core/models/server.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { ServerSharedService } from 'src/app/shared/server-shared.service';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
import { PopupFeedback } from 'src/app/core/models/popup-feedback.model';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  messagesList: Message[] = [];
  message!: Message;
  idServer!: number;
  idRoom!: number;
  user!: PseudoPassword;
  room!: Room;
  server!: Server;
  
  constructor(
    private serverService: ServerService,
    private localStorageService: LocalStorageService,
    private serverSharedService: ServerSharedService,
    private pfs: PopupFeedbackService
  ){}


  ngOnInit(): void {
    this.user = this.localStorageService.getPseudoPassword();

    this.serverSharedService.getServerShared().subscribe(serverInfo => {
      this.updateDisplay(serverInfo.currentServerId, serverInfo.currentRoomId)
    });

    /*setInterval(() => {
      this.updateDisplay(this.idServer, this.idRoom);
    }, 4000);*/
  }

  ngAfterViewChecked(): void {
    const messagesContainer = document.querySelector('#messages-container');
    if (messagesContainer) {
      messagesContainer.scrollTop = messagesContainer.scrollHeight - messagesContainer.clientHeight;
    }
  }

  updateDisplay(serverId : number, roomId : number): void { 
    this.serverService.getRoomInServerById(serverId, roomId).subscribe(room => {
      this.room = room;
      this.messagesList = room.messageList.sort((a,b)=>{return a.id - b.id});
      this.idRoom = roomId;
      this.idServer = serverId;
    });
  }

  onAddMessageReceive(message: string){
    let dto = {user : this.user, content : message} ;
    this.serverService.addMessage(dto, this.idServer, this.idRoom).subscribe(v => {
      console.log(v);
      this.updateDisplay(this.idServer, this.idRoom);
    });
  }
    
  onDeleteMessageReceive(idMessage: number){
    this.user = this.localStorageService.getPseudoPassword();
    this.serverSharedService.getServerShared().subscribe(serverInfo => {
      this.serverService.deleteMessageInRoomInServerById(serverInfo.currentServerId, serverInfo.currentRoomId, idMessage, this.user).subscribe(v => {
        console.log(v);
        this.updateDisplay(this.idServer, this.idRoom);
      });
    });
  }

  onUpdateMessageReceive(message: Message): void {
    if (message && message.id !== undefined) { 
      this.user = this.localStorageService.getPseudoPassword();
      let dto = { user : this.user, content : message.content};
      this.serverService.updateMessage(message.id, dto).subscribe(v => {
        console.log(v);
        this.updateDisplay(this.idServer, this.idRoom);
      });
    }
  }

  onUpdateNameRoomReceive(room: string){
    this.serverService.updateRoom(this.idServer, this.idRoom, room).subscribe(v =>{
      console.log(v);
      this.pfs.setFeed(new PopupFeedback("Salon bien renommé ✏️", "valid"));
      this.updateDisplay(this.idServer, this.idRoom);
    });
  }

  onDeleteRoomReceive(idRoom: number){
    this.serverService.deleteRoomInServerById(this.idServer, idRoom, this.user).subscribe(v => {
      console.log(v);
      this.pfs.setFeed(new PopupFeedback("Salon bien supprimé 🗑️", "valid"));
    });
  }

}