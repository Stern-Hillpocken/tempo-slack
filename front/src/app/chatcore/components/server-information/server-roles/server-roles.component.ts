import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { ServerSharedService } from "src/app/shared/server-shared.service";
import { ServerService } from "src/app/chatcore/services/server.service";
import { Role } from "src/app/core/models/role.model";
import { FormBuilder, FormGroup } from "@angular/forms";

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

  @Output()
  addRoleEmitter: EventEmitter<any> = new EventEmitter();

  constructor(
    private serverService: ServerService,
    private serverSharedService: ServerSharedService,
    private fb: FormBuilder
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
      roleName:[""],
    });
  }

  addRole(): void {
    console.log(this.formAddRole.value);
    this.addRoleEmitter.emit(this.formAddRole.value);
     this.formAddRole = this.fb.group({
      userName: [""],
      roleName:[""],
     });
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
}
