import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Room } from 'src/app/core/models/room.model';


@Component({
  selector: 'app-message-form',
  templateUrl: './message-form.component.html',
  styleUrls: ['./message-form.component.scss']
})
export class MessageFormComponent implements OnInit{

  @Input()
  roomTitle!: string;

  @Output()
  addMessageEmitter: EventEmitter<string> = new EventEmitter();

  formMessage!: FormGroup;

  constructor(private fb: FormBuilder){}

  ngOnInit(): void {
    this.formMessage = this.fb.group({
      content:['']
    });
  }

  addMessage(): void {
    this.addMessageEmitter.emit(this.formMessage.value.content);
    this.formMessage = this.fb.group({
      content:['']
    });
  }
}
