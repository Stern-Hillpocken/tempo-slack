import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ServerService } from "src/app/chatcore/services/server.service";
import { Role } from "src/app/core/models/role.model";

@Component({
  selector: "app-server-roles",
  templateUrl: "./server-roles.component.html",
  styleUrls: ["./server-roles.component.scss"],
})
export class ServerRolesComponent implements OnInit {
  id!: number;
  roleList: Role[] = [];

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get("id"));
    this.serverService.getServerById(this.id).subscribe((server) => {
      this.roleList = server.roleList;
    });
  }
}
