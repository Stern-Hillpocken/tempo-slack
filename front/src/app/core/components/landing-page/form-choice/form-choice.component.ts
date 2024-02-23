import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-form-choice',
  templateUrl: './form-choice.component.html',
  styleUrls: ['./form-choice.component.scss']
})
export class FormChoiceComponent {

  @Input()
  formChoiceToDisplay!: "login" | "signin";

  @Output()
  formChoiceEmitter: EventEmitter<"login" | "signin"> = new EventEmitter();

  changeFormDisplay(value: "login" | "signin"): void {
    this.formChoiceEmitter.emit(value);
  }

}
