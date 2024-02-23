import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ServerService } from "src/app/chatcore/services/server.service";
import { Room } from "src/app/core/models/room.model";

@Component({
  selector: "app-server-rooms",
  templateUrl: "./server-rooms.component.html",
  styleUrls: ["./server-rooms.component.scss"],
})
export class ServerRoomsComponent implements OnInit {
  id!: number;
  roomList: Room[] = [];
  selected: any;

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get("id"));
    this.serverService.getServerById(this.id).subscribe((server) => {
      this.roomList = server.roomList;
    });
  }

  update() {
    // this.chatService.update(); Communication avec le chat service
    this.selected = !this.selected;
  }
}
