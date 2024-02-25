import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Message } from 'src/app/core/models/message';

import { Room } from 'src/app/core/models/room.model';


@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {

  formUpdateMessage!: FormGroup;
  popUp: boolean =false;
  popUpDelete: boolean = false;
 
  @Input()
  message!: Message;
  @Input()
  room!: Room;

  @Output()
  updateMessageEmitter :EventEmitter<Message> = new EventEmitter();
  @Output()
  deleteMessageEmitter: EventEmitter<number> = new EventEmitter();
  

  constructor(private fb: FormBuilder){}

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

 

   
      
   
      


    

