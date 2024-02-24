import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MessageStoreService } from 'src/app/chatcore/services/message-store.service';
import { MessagesService } from 'src/app/chatcore/services/message.service';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { Message } from 'src/app/core/models/message';
import { PseudoPassword } from 'src/app/core/models/pseudo-password.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';

@Component({
  selector: 'app-message-form',
  templateUrl: './message-form.component.html',
  styleUrls: ['./message-form.component.scss']
})
export class MessageFormComponent implements OnInit{
formMessage!: FormGroup;
idServer! : number;
idRoom! : number;
user! : PseudoPassword;
message!: Message;




constructor(private fb: FormBuilder, private messageService : MessagesService, private serverService : ServerService, private activatedRoute: ActivatedRoute, private localStorageService : LocalStorageService, private messageStoreService : MessageStoreService ){

}
ngOnInit(): void {
  // console.log(this.selected)
   this.formMessage = this.fb.group({
     content:['']
     
   })
 }

 save(){
  
  console.log(this.formMessage.value);
  
  this.user = this.localStorageService.getPseudoPassword();
  this.message = {user : this.user,...this.formMessage.value} ;
  this.serverService.addMessage(this.message, this.idServer, this.idRoom).subscribe(v =>console.log(v))
}
 
 }

