import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { ServerService } from "src/app/chatcore/services/server.service";
import { Room } from "src/app/core/models/room.model";
import { LocalStorageService } from "src/app/shared/local-storage.service";

@Component({
  selector: "app-server-rooms",
  templateUrl: "./server-rooms.component.html",
  styleUrls: ["./server-rooms.component.scss"],
})
export class ServerRoomsComponent implements OnInit {
  id!: number;
  roomList: Room[] = [];
  selected: any;
  popUp: any;
  addPopUp: any;
  formRoom!: FormGroup;

  constructor(
    private serverService: ServerService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private localStorage: LocalStorageService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get("id"));
    this.serverService.getServerById(this.id).subscribe((server) => {
      this.roomList = server.roomList;
    });
    this.formRoom = this.fb.group({
      title: ["", [Validators.required, Validators.minLength(1)]],
    });
  }

  update() {
    // this.chatService.update(); Communication avec le chat service
    this.selected = !this.selected;
  }

  addRoom() {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get("id"));
    this.serverService.getServerById(this.id).subscribe((server) => {
      this.serverService.addRoomInServerById(this.formRoom.value, this.id);
    });
  }
}
