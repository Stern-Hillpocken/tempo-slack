import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';


@Component({
  selector: 'app-message-form',
  templateUrl: './message-form.component.html',
  styleUrls: ['./message-form.component.scss']
})
export class MessageFormComponent implements OnInit{
formMessage!: FormGroup;


@Output()
addMessageEmitter: EventEmitter<string> = new EventEmitter();

constructor(private fb: FormBuilder){}

ngOnInit(): void {
   this.formMessage = this.fb.group({
     content:['']
   })
 }
  addMessage(): void {
   this.addMessageEmitter.emit(this.formMessage.value.content);
   this.formMessage = this.fb.group({
    content:['']
  })
 }
}
