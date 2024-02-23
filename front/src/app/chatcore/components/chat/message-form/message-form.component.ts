import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MessagesService } from 'src/app/chatcore/services/message.service';

@Component({
  selector: 'app-message-form',
  templateUrl: './message-form.component.html',
  styleUrls: ['./message-form.component.scss']
})
export class MessageFormComponent implements OnInit{
formMessage!: FormGroup;



constructor(private fb: FormBuilder, private messageService : MessagesService ){

}
ngOnInit(): void {
  // console.log(this.selected)
   this.formMessage = this.fb.group({
     content:['']
     
   })
 }

 save(){
  
  console.log(this.formMessage.value);


  this.messageService.addMessage(this.formMessage.value).subscribe(v =>
     console.log(v));
     //this.messagesStoreService.addClient(this.formMessage.value);
   
}


}
