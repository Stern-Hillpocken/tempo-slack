import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ServerService } from "src/app/chatcore/services/server.service";
import { Server } from "src/app/core/models/server.model";

@Component({
  selector: "app-server-name",
  templateUrl: "./server-name.component.html",
  styleUrls: ["./server-name.component.scss"],
})
export class ServerNameComponent implements OnInit {
  id!: number;
  serverName: string = ''; // Nom initial du serveur

  constructor(private serverService: ServerService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get("id"));
    this.serverService.getServerById(this.id).subscribe((server) => {
      this.serverName = server.name; 
    });
  }

 /*  editServerName(newName: string): void {
    const updatedServer = { name: newName }; 
    this.serverService.updateServer(this.id, updatedServer).subscribe({
      next: (updated) => {
        this.serverName = updated.name; 
        console.log('Server name updated successfully!');
      },
      error: (error) => console.error('There was an error updating the server name', error)
    });
  } */
}
