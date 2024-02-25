import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent {

  @Output()
  lougoutEmitter: EventEmitter<void> = new EventEmitter();

  logout(): void {
    this.lougoutEmitter.emit();
  }
}
