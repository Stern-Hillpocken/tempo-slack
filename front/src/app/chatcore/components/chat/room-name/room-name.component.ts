import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ServerService } from 'src/app/chatcore/services/server.service';

@Component({
  selector: 'app-room-name',
  templateUrl: './room-name.component.html',
  styleUrls: ['./room-name.component.scss']
})
export class RoomNameComponent {

  idServer!: number;
  idRoom!: number;
  roomName! : string;
 
  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute){

  }
  ngOnInit() {

    this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
    this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
    
    this.serverService.getRoomInServerById(this.idServer, this.idRoom).subscribe(room => {
      this.roomName = room.title;
      console.log(this.roomName)
        
    
    })

          
   
  }


}
