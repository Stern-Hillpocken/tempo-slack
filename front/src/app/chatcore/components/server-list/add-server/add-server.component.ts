import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-server',
  templateUrl: './add-server.component.html',
  styleUrls: ['./add-server.component.scss']
})
export class AddServerComponent {

  @Output()
  addServerEmitter: EventEmitter<string> = new EventEmitter();

  isPopupDisplay: boolean = false;

  formAddServer!: FormGroup;

  constructor(
    private fb: FormBuilder
  ){}

  ngOnInit(): void {
    this.formAddServer = this.fb.group({
      name: ["Octoserv"]
    });
  }

  openPopup(): void {
    this.isPopupDisplay = true;
  }

  closePopup(): void {
    this.isPopupDisplay = false;
  }

  addServer(): void {
    this.addServerEmitter.emit(this.formAddServer.value.name);
    this.closePopup();
  }
}
