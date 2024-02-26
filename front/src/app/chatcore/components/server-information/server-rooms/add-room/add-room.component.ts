import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-add-room",
  templateUrl: "./add-room.component.html",
  styleUrls: ["./add-room.component.scss"],
})
export class AddRoomComponent implements OnInit {
  @Output()
  addRoomEmitter: EventEmitter<string> = new EventEmitter();

  isPopupRoomDisplay: boolean = false;
  formAddRoom!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formAddRoom = this.fb.group({
      name: ["OctoRoom", [Validators.required, Validators.minLength(1)]],
    });
  }

  openPopup(): void {
    this.isPopupRoomDisplay = true;
  }

  closePopup(): void {
    this.isPopupRoomDisplay = false;
  }

  addRoom(): void {
    this.addRoomEmitter.emit(this.formAddRoom.value.name);
    this.closePopup();
  }
}
