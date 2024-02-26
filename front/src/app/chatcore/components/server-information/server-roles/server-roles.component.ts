import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { ServerSharedService } from "src/app/shared/server-shared.service";
import { ServerService } from "src/app/chatcore/services/server.service";
import { Role } from "src/app/core/models/role.model";
import { FormBuilder, FormGroup } from "@angular/forms";
import { PopupFeedbackService } from "src/app/shared/popup-feedback.service";
import { PopupFeedback } from "src/app/core/models/popup-feedback.model";
import { LocalStorageService } from "src/app/shared/local-storage.service";

@Component({
  selector: "app-server-roles",
  templateUrl: "./server-roles.component.html",
  styleUrls: ["./server-roles.component.scss"],
})
export class ServerRolesComponent implements OnInit {
  serverId!: number;
  roleList: Role[] = [];
  formAddRole!: FormGroup;
  role!: Role;
  popUp: boolean = false;
  popUpDelete: boolean = false;
  isRoleInServer: boolean = false;

  @Output()
  addRoleEmitter: EventEmitter<any> = new EventEmitter();

  constructor(
    private serverService: ServerService,
    private serverSharedService: ServerSharedService,
    private fb: FormBuilder,
    private pfs: PopupFeedbackService,
    private lss: LocalStorageService
  ) {}

  ngOnInit(): void {
    this.serverSharedService.getServerShared().subscribe((serverInfo) => {
      this.serverId = serverInfo.currentServerId;
      this.serverService.getServerById(this.serverId).subscribe((server) => {
        this.roleList = server.roleList;
        console.log(serverInfo);
      });
    });
    this.formAddRole = this.fb.group({
      userName: [""],
      roleName: [""],
    });
  }

  addRole(): void {
    console.log(this.formAddRole.value);
    this.addRoleEmitter.emit(this.formAddRole.value);
    this.formAddRole = this.fb.group({
      userName: [""],
      roleName: [""],
    });
  }

  onAddRoleReceive(roleName: string): void {
    this.isRoleInServer = false;
    this.serverSharedService.getServerShared().subscribe((shi) => (this.serverId = shi.currentServerId));
    for (let role of this.roleList) {
      if (role.name == roleName) {
        this.pfs.setFeed(new PopupFeedback("Ce Rôle existe déjà dans le serveur !", "error"));
        this.isRoleInServer = true;
        break;
      }
    }
    if (!this.isRoleInServer) {
      this.serverService
        .addRoleToServer(this.serverId, { name: roleName, user: this.lss.getPseudoPassword() })
        .subscribe((resp) => {
          this.pfs.setFeed(new PopupFeedback("Rôle ajouté avec succès !", "valid"));
          this.updateDisplay();
        });
    }
  }

  deleteRole() {}

  openPopup() {
    this.popUp = true;
    // Initialiser le formulaire avec le contenu du message
    //this.formAddRole = this.fb.group({
    // name: [this.role.name],
    // });
    // console.log(this.role.name);
  }
  closePopup() {
    this.popUp = false;
  }
  openPopupDelete() {
    this.popUpDelete = true;
  }
  closePopupDelete() {
    this.popUpDelete = false;
  }

  updateDisplay(): void {
    this.serverService.getServerById(this.serverId).subscribe((server) => {
      this.roleList = server.roleList;
    });
  }
}
