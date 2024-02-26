import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Room } from 'src/app/core/models/room.model';

@Component({
  selector: 'app-room-name',
  templateUrl: './room-name.component.html',
  styleUrls: ['./room-name.component.scss']
})
export class RoomNameComponent {

  @Input()
  room!: Room;

  @Output()
  updateNameRoomEmitter :EventEmitter<string> = new EventEmitter();
  @Output()
  deleteRoomEmitter: EventEmitter<number> = new EventEmitter();

  formUpdateRoom!: FormGroup;
  popUpUpdate: boolean = false;
  popUpDelete: boolean = false;

  constructor(private fb: FormBuilder){}

    updateNameRoom(): void{
      console.log(this.formUpdateRoom.value)
      this.updateNameRoomEmitter.emit(this.formUpdateRoom.value.title)
      this.closePopupUpdate();
    }
    deleteRoom(): void {
      this.deleteRoomEmitter.emit(this.room.id);
      this.closePopupDelete();
    }

    openPopupUpdate(){
      this.popUpUpdate = true;
     // Initialiser le formulaire avec le contenu du message
    this.formUpdateRoom = this.fb.group({
        title: [this.room.title]
      });     
    }
    closePopupUpdate(){
      this.popUpUpdate = false;
    }        
    openPopupDelete(){
      this.popUpDelete = true;
    }
    closePopupDelete(){
      this.popUpDelete = false;
    }
};

