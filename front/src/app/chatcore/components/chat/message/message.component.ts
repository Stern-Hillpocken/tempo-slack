import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MessageStoreService } from 'src/app/chatcore/services/message-store.service';
import { MessagesService } from 'src/app/chatcore/services/message.service';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { Message } from 'src/app/core/models/message';
import { PseudoPassword } from 'src/app/core/models/pseudo-password.model';
import { Room } from 'src/app/core/models/room.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {
  formMessage!: FormGroup;
  formUpdateMessage!: FormGroup;
  idServer!: number;
  idRoom!: number;
  idMessage!: number;
  user!:PseudoPassword;
  popUp: boolean =false;
  popUpDelete: boolean = false;
  

 
  selected: any;

  @Input()
  message!: Message;
  @Input()
  room!: Room;

  @Output()
  updateMessageEmitter :EventEmitter<Message> = new EventEmitter();
  @Output()
  deleteMessageEmitter: EventEmitter<number> = new EventEmitter();
  

  constructor(private messageService : MessagesService,private serverService: ServerService, private activatedRoute: ActivatedRoute, private messageStoreService : MessageStoreService, private localStorageService : LocalStorageService, private fb: FormBuilder){

    
  }
  ngOnInit() {
  }

    updateMessage(): void{
      console.log(this.formUpdateMessage.value)
      this.updateMessageEmitter.emit(this.formUpdateMessage.value)
      this.closePopup();
    }
    deleteMessage(): void {
      this.deleteMessageEmitter.emit(this.message.id);
      this.closePopupDelete();
    }

    openPopup(){
           this.popUp = true;
          
        // Initialiser le formulaire avec le contenu du message
         this.formUpdateMessage = this.fb.group({
            content: [this.message.content],
            id:[this.message.id] 
         });
        }
        closePopup(){
          this.popUp = false;
       }
    openPopupDelete(){
      this.popUpDelete = true;
     }
        closePopupDelete(){
          this.popUpDelete = false;
        }
            
    
    };

 
    // updateMessage(){
    //   this.user = this.localStorageService.getPseudoPassword();
    //   this.message = {... this.formUpdateMessage.value, user : this.user}
    //   console.log(this.message)
    //   this.idMessage = Number(this.message.id)
    //   this.serverService.updateMessage( this.idMessage, this.message,).subscribe(v =>{
    //      console.log("hello");
    //      this.closePopup();
    //   }
      
  //       // this.clientsStoreService.addClient(newClient)

   
      
   
      


    

