import { Component, EventEmitter, OnInit, Output } from '@angular/core';
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


@Output()
addMessageEmitter: EventEmitter<string> = new EventEmitter();

constructor(private fb: FormBuilder, private messageService : MessagesService, private serverService : ServerService, private activatedRoute: ActivatedRoute, private localStorageService : LocalStorageService, private messageStoreService : MessageStoreService ){}

ngOnInit(): void {
  // console.log(this.selected)
   this.formMessage = this.fb.group({
     content:['']
   })
 }
  addMessage(): void {
   this.addMessageEmitter.emit(this.formMessage.value.content);
 }

}
