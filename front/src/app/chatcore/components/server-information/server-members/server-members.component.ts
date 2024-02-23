import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ServerService } from "src/app/chatcore/services/server.service";
import { User } from "src/app/core/models/user.model";

@Component({
  selector: "app-server-members",
  templateUrl: "./server-members.component.html",
  styleUrls: ["./server-members.component.scss"],
})
export class ServerMembersComponent implements OnInit {
  id!: number;
  userList: User[] = [];

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get("id"));
    this.serverService.getServerById(this.id).subscribe((server) => {
      this.userList = server.userList;
    });
  }
}
