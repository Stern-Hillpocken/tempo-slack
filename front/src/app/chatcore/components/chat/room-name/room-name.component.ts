import { Component, Input } from '@angular/core';
import { Room } from 'src/app/core/models/room.model';

@Component({
  selector: 'app-room-name',
  templateUrl: './room-name.component.html',
  styleUrls: ['./room-name.component.scss']
})
export class RoomNameComponent {

  @Input()
  room!: Room;
}
