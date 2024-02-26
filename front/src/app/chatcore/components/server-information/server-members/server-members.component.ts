import { Component, OnInit } from "@angular/core";
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
  isUserInServer: boolean = false;
  membersDisplayed: boolean = false;

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
    this.isUserInServer = false;
    this.sss.getServerShared().subscribe((shi) => (this.currentServerId = shi.currentServerId));
    for (let user of this.userList) {
      if (user.pseudo == pseudo) {
        this.pfs.setFeed(new PopupFeedback("Membre déjà présent dans le serveur !", "error"));
        this.isUserInServer = true;
        break;
      }
    }
    if (!this.isUserInServer) {
      this.serverService
        .addUser(this.currentServerId, { userPseudoToAdd: pseudo, user: this.lss.getPseudoPassword() })
        .subscribe((resp) => {
          this.pfs.setFeed(new PopupFeedback("Membre ajouté avec succès !", "valid"));
          this.updateDisplay();
        });
    }
  }

  onDeleteMemberReceive(pseudo: string): void {
    this.isUserInServer = true;
    this.sss.getServerShared().subscribe((shi) => (this.currentServerId = shi.currentServerId));
    if (this.lss.getPseudoPassword().pseudo != pseudo) {
      for (let user of this.userList) {
        if (user.pseudo == pseudo) {
          this.serverService
            .deleteUser(this.currentServerId, { user: this.lss.getPseudoPassword(), userPseudoToUpdate: pseudo })
            .subscribe((resp) => {
              this.pfs.setFeed(new PopupFeedback("Membre retiré du serveur avec succès !", "valid"));
              this.updateDisplay();
            });
          this.isUserInServer = false;
          break;
        }
      }
      if (this.isUserInServer) {
        this.pfs.setFeed(new PopupFeedback("Cet utilisateur n'existe pas dans le serveur !", "error"));
        this.isUserInServer = true;
      }
    } else {
      this.pfs.setFeed(new PopupFeedback("Vous ne pouvez pas vous retirer du serveur !", "error"));
    }
  }

  updateDisplay(): void {
    this.serverService.getServerById(this.currentServerId).subscribe((server) => {
      this.userList = server.userList;
    });
  }

  membersFoldToggle(): void {
    this.membersDisplayed = !this.membersDisplayed;
  }
}
