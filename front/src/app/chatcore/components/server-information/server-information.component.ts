import { Component } from "@angular/core";
import { User } from "src/app/core/models/user.model";
import { ServerService } from "../../services/server.service";
import { ServerSharedService } from "src/app/shared/server-shared.service";
import { PseudoPassword } from "src/app/core/models/pseudo-password.model";
import { Role } from "src/app/core/models/role.model";
import { FormGroup } from "@angular/forms";
import { LocalStorageService } from "src/app/shared/local-storage.service";


@Component({
  selector: "app-server-information",
  templateUrl: "./server-information.component.html",
  styleUrls: ["./server-information.component.scss"],
})
export class ServerInformationComponent {
  [x: string]: any;
  serverId!: number;
  formAddRole!: FormGroup;

  user!: PseudoPassword;
  userAll!: User;
  constructor(
    private serverService: ServerService,
    private serverSharedService: ServerSharedService,
    private localStorageService : LocalStorageService
  ) {}

  onAddRoleReceive(name: any): void {
    console.log(name);
    let dto = {
      user: this.localStorageService.getPseudoPassword(),
      roleName: name.roleName,
      userToAdd: name.userName,
    };
    console.log(dto)
    this.serverSharedService.getServerShared().subscribe((serverInfo) => {
      this.serverId = serverInfo.currentServerId;
      this.serverService.addRoleToUser(this.serverId, dto).subscribe((v) => {
        console.log(v);
        //this.updateDisplay(this.idServer, this.idRoom);
      });
    });
  }
}
