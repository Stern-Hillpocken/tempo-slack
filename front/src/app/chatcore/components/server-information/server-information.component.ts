import { Component } from "@angular/core";
import { User } from "src/app/core/models/user.model";
import { ServerService } from "../../services/server.service";
import { ServerSharedService } from "src/app/shared/server-shared.service";
import { PseudoPassword } from "src/app/core/models/pseudo-password.model";
import { FormGroup } from "@angular/forms";
import { LocalStorageService } from "src/app/shared/local-storage.service";
import { PopupFeedbackService } from "src/app/shared/popup-feedback.service";
import { PopupFeedback } from "src/app/core/models/popup-feedback.model";


@Component({
  selector: "app-server-information",
  templateUrl: "./server-information.component.html",
  styleUrls: ["./server-information.component.scss"],
})
export class ServerInformationComponent {
  serverId!: number;
  formAddRole!: FormGroup;

  user!: PseudoPassword;
  userAll!: User;
  constructor(
    private serverService: ServerService,
    private serverSharedService: ServerSharedService,
    private localStorageService : LocalStorageService,
    private pfs: PopupFeedbackService
  ) {}

  onAddRoleToUserReceive(name: any): void {
    let dto = {
      user: this.localStorageService.getPseudoPassword(),
      roleName: name.roleName,
      userToAdd: name.userName,
    };
    this.serverSharedService.getServerShared().subscribe((serverInfo) => {
      this.serverId = serverInfo.currentServerId;
      this.serverService.addRoleToUser(this.serverId, dto).subscribe((v) => {
        this.pfs.setFeed(new PopupFeedback("Le rôle ["+name.roleName+"] été attribué à "+name.userName+" !", "valid"));
        //this.updateDisplay(this.idServer, this.idRoom);
      });
    });
  }
}
