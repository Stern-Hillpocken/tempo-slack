import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Message } from 'src/app/core/models/message';
import { PseudoPassword } from 'src/app/core/models/pseudo-password.model';

import { Room } from 'src/app/core/models/room.model';


@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {

  formUpdateMessage!: FormGroup;
  popUpEdit: boolean =false;
  popUpDelete: boolean = false;
 
  @Input()
  message!: Message;
  @Input()
  userPseudo: string = "";

  @Output()
  updateMessageEmitter :EventEmitter<Message> = new EventEmitter();
  @Output()
  deleteMessageEmitter: EventEmitter<number> = new EventEmitter();
  

  constructor(private fb: FormBuilder){}

  updateMessage(): void{
    console.log(this.formUpdateMessage.value)
    this.updateMessageEmitter.emit(this.formUpdateMessage.value)
    this.closePopupEdit();
  }

  deleteMessage(): void {
    this.deleteMessageEmitter.emit(this.message.id);
    this.closePopupDelete();
  }

  openPopupEdit(){
    this.popUpEdit = true;
    this.popUpDelete = false;
    // Initialiser le formulaire avec le contenu du message
    this.formUpdateMessage = this.fb.group({
      content: [this.message.content],
      id:[this.message.id] 
    });     
  }

  closePopupEdit(){
    this.popUpEdit = false;
  }   

  openPopupDelete(){
    this.popUpDelete = true;
    this.popUpEdit = false;
  }

  closePopupDelete(){
    this.popUpDelete = false;
  }

  convertDate(d: Date): string {
    let date = d.toString();
    date = date.slice(8,10) + "/" + date.slice(5,7) + "/" + date.slice(2,4) + " " + date.slice(11,16);
    return date;
  }
};

 

   
      
   
      


    

