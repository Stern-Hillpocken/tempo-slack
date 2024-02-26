import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { ServerService } from "src/app/chatcore/services/server.service";
import { PopupFeedback } from "src/app/core/models/popup-feedback.model";
import { Room } from "src/app/core/models/room.model";
import { LocalStorageService } from "src/app/shared/local-storage.service";
import { PopupFeedbackService } from "src/app/shared/popup-feedback.service";
import { ServerSharedService } from "src/app/shared/server-shared.service";

@Component({
  selector: "app-server-rooms",
  templateUrl: "./server-rooms.component.html",
  styleUrls: ["./server-rooms.component.scss"],
})
export class ServerRoomsComponent implements OnInit {
  currentServerId!: number;
  roomList: Room[] = [];
  selected: any;
  popUp: any;
  addPopUp: any;
  formRoom!: FormGroup;
  currentRoomId!: number;
  roomDisplayed: boolean = true;

  constructor(
    private serverService: ServerService,
    private pfs: PopupFeedbackService,
    private lss: LocalStorageService,
    private sss: ServerSharedService
  ) {}

  ngOnInit(): void {
    this.sss.getServerShared().subscribe((shi) => {
      this.currentServerId = shi.currentServerId;
      this.currentRoomId = shi.currentRoomId;
      this.serverService.getServerById(this.currentServerId).subscribe((server) => {
        this.roomList = server.roomList;
      });
    });
  }

  onAddRoomReceive(roomTitle: string): void {
    this.sss.getServerShared().subscribe((shi) => (this.currentServerId = shi.currentServerId));
    this.serverService
      .addRoom(this.currentServerId, roomTitle, { title: roomTitle, user: this.lss.getPseudoPassword() })
      .subscribe((resp) => {
        this.pfs.setFeed(new PopupFeedback("Room créée avec succès !", "valid"));
        this.updateDisplay();
      });
  }

  updateDisplay(): void {
    this.serverService.getServerById(this.currentServerId).subscribe((server) => {
      this.roomList = server.roomList;
    });
  }

  onChangeRoomReceive(room: Room): void {
    this.sss.setRoomId(room.id);
  }

  roomFoldToggle(): void {
    this.roomDisplayed = !this.roomDisplayed;
  }
}
