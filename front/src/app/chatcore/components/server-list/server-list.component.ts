import { Component } from '@angular/core';
import { Server } from 'src/app/core/models/server.model';
import { ServerService } from '../../services/server.service';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
import { PopupFeedback } from 'src/app/core/models/popup-feedback.model';

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
    private pfs: PopupFeedbackService
  ){}

  ngOnInit(): void {
    this.updateDisplay();
  }

  onAddServerReceive(serverName: string): void {
    this.serverService.addServer({name: serverName, user: this.lss.getPseudoPassword()}).subscribe(resp => {
      this.pfs.setFeed(new PopupFeedback("Serveur crée avec succès !", "valid"));
      this.updateDisplay();
    });
  }

  updateDisplay(): void {
    this.serverService.getServersOfUser(this.lss.getPseudoPassword()).subscribe(servers => {
      this.servers = servers;
    });
  }

}
