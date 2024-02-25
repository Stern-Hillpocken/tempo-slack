import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Room } from 'src/app/core/models/room.model';

@Component({
  selector: 'app-room-name',
  templateUrl: './room-name.component.html',
  styleUrls: ['./room-name.component.scss']
})
export class RoomNameComponent {
  formUpdateRoom!: FormGroup;
  popUp: boolean =false;
  popUpDelete: boolean = false;

  @Input()
  room!: Room;

  @Output()
  updateNameRoomEmitter :EventEmitter<string> = new EventEmitter();
  @Output()
  deleteRoomEmitter: EventEmitter<number> = new EventEmitter();

  constructor(private fb: FormBuilder){}

  ngOnInit() {
  }

    updateNameRoom(): void{
      console.log(this.formUpdateRoom.value)
      this.updateNameRoomEmitter.emit(this.formUpdateRoom.value.title)
      this.closePopup();
    }
    deleteRoom(): void {
      this.deleteRoomEmitter.emit(this.room.id);
      this.closePopupDelete();
    }

    openPopup(){
      this.popUp = true;
     // Initialiser le formulaire avec le contenu du message
    this.formUpdateRoom = this.fb.group({
        title: [this.room.title]
      });     
    }
    closePopup(){
      this.popUp = false;
    }        
    openPopupDelete(){
      this.popUpDelete = true;
    }
    closePopupDelete(){
      this.popUpDelete = false;
    }
};

