import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Room } from "src/app/core/models/room.model";

@Component({
  selector: "app-room-display",
  templateUrl: "./room-display.component.html",
  styleUrls: ["./room-display.component.scss"],
})
export class RoomDisplayComponent {
  @Input()
  currentRoomId!: number;

  @Input()
  room!: Room;

  @Output()
  changeRoomEmitter: EventEmitter<Room> = new EventEmitter();

  changeRoom(): void {
    this.changeRoomEmitter.emit(this.room);
  }
}
