import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss']
})
export class DeleteUserComponent {

  @Output()
  deleteUserEmitter: EventEmitter<void> = new EventEmitter();

  isDeletePopup: boolean = false;

  deleteUserPopup(): void {
    this.isDeletePopup = !this.isDeletePopup;
  }

  deleteUserConfirmed(): void {
    this.deleteUserEmitter.emit();
  }

}
