import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ServerService } from "src/app/chatcore/services/server.service";
import { PopupFeedback } from "src/app/core/models/popup-feedback.model";
import { User } from "src/app/core/models/user.model";
import { LocalStorageService } from "src/app/shared/local-storage.service";
import { PopupFeedbackService } from "src/app/shared/popup-feedback.service";
import { ServerSharedService } from "src/app/shared/server-shared.service";

@Component({
  selector: "app-server-members",
  templateUrl: "./server-members.component.html",
  styleUrls: ["./server-members.component.scss"],
})
export class ServerMembersComponent implements OnInit {
  currentServerId!: number;
  userList: User[] = [];

  constructor(
    private serverService: ServerService,
    private sss: ServerSharedService,
    private lss: LocalStorageService,
    private pfs: PopupFeedbackService
  ) {}

  ngOnInit(): void {
    this.sss.getServerShared().subscribe((shi) => {
      this.currentServerId = shi.currentServerId;
      this.serverService.getServerById(this.currentServerId).subscribe((server) => {
        this.userList = server.userList;
      });
    });
  }

  onAddMemberReceive(pseudo: string): void {
    console.log(pseudo);
    this.sss.getServerShared().subscribe((shi) => (this.currentServerId = shi.currentServerId));
    this.serverService
      .addUser(this.currentServerId, { userPseudoToAdd: pseudo, user: this.lss.getPseudoPassword() })
      .subscribe((resp) => {
        this.pfs.setFeed(new PopupFeedback("Membre ajouté avec succès !", "valid"));
        this.updateDisplay();
      });
  }

  updateDisplay(): void {
    this.serverService.getServerById(this.currentServerId).subscribe((server) => {
      this.userList = server.userList;
    });
  }
}
