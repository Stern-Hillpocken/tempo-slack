import { Component } from '@angular/core';
import { Server } from 'src/app/core/models/server.model';
import { ServerService } from '../../services/server.service';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
import { PopupFeedback } from 'src/app/core/models/popup-feedback.model';
import { ServerSharedService } from 'src/app/shared/servershared.service';

@Component({
  selector: 'app-server-list',
  templateUrl: './server-list.component.html',
  styleUrls: ['./server-list.component.scss']
})
export class ServerListComponent {

  servers!: Server[];

  constructor(
    private serverService: ServerService,
    private lss: LocalStorageService,
    private pfs: PopupFeedbackService,
    private serverSharedService: ServerSharedService
  ){}

  ngOnInit(): void {
    this.updateDisplay();
  }

  onAddServerReceive(serverName: string): void {
    let id = 0;
    this.serverService.addServer({name: serverName, user: this.lss.getPseudoPassword()}).subscribe(resp => {
      this.pfs.setFeed(new PopupFeedback("Serveur crée avec succès !", "valid"));
      this.updateDisplay();
      id++;
      console.log("id emit: " + id);
      
      this.serverSharedService.changeServerId(id); 
    });
  }

  updateDisplay(): void {
    this.serverService.getServersOfUser(this.lss.getPseudoPassword()).subscribe(servers => {
      this.servers = servers;
      console.log(this.servers);
      if (this.servers.length > 0) {
        // Supposons que le dernier serveur de la liste est le nouveau serveur
        const newServer = this.servers[this.servers.length - 1];
        this.serverSharedService.changeServerId(newServer.id);
      }
    });
  }
  

}
