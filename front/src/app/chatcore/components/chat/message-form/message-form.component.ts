import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
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




constructor(private fb: FormBuilder, private messageService : MessagesService, private serverService : ServerService, private activatedRoute: ActivatedRoute, private localStorageService : LocalStorageService ){

}
ngOnInit(): void {
  // console.log(this.selected)
   this.formMessage = this.fb.group({
     content:['']
     
   })
 }

 save(){
  
  console.log(this.formMessage.value);
  
  this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
  this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
  this.user = this.localStorageService.getPseudoPassword();
  this.message = {...this.formMessage.value, user : this.user};
  console.log(this.message)
  this.serverService.getRoomInServerById(this.idServer, this.idRoom).subscribe((room) =>
  {
     this.messageService.addMessage(this.formMessage.value,this.idServer, this.idRoom).subscribe((v) =>
     console.log(v));
  })
  

 
     //this.messagesStoreService.addClient(this.formMessage.value);
   


 }
}
