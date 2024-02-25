import { Component } from '@angular/core';
import { Server } from 'src/app/core/models/server.model';
import { ServerService } from '../../services/server.service';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
import { PopupFeedback } from 'src/app/core/models/popup-feedback.model';

import { ServerSharedService } from 'src/app/shared/server-shared.service';

import { UserPublic } from 'src/app/core/models/user-public.model';
import { UserService } from 'src/app/shared/user.service';
import { Router } from '@angular/router';
import { ServerSharedInfo } from 'src/app/core/models/server-shared-info.model';


@Component({
  selector: 'app-server-list',
  templateUrl: './server-list.component.html',
  styleUrls: ['./server-list.component.scss']
})
export class ServerListComponent {

  user!: UserPublic;

  currentServerId!: number;

  servers: Server[] = [];

  constructor(
    private userService: UserService,
    private serverService: ServerService,
    private lss: LocalStorageService,
    private pfs: PopupFeedbackService,
    private sss: ServerSharedService,
    private router: Router

  ){}

  ngOnInit(): void {
    this.updateDisplay();

    this.sss.getServerShared().subscribe(shi => this.currentServerId = shi.currentServerId);

    this.userService.getPublic(this.lss.getPseudoPassword().pseudo).subscribe(u => this.user = u);
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
      if (this.servers.length > 0) {
        // Supposons que le dernier serveur de la liste est le nouveau serveur
        const lastServer: Server = this.servers[this.servers.length - 1];
        this.sss.setServerShared(new ServerSharedInfo(lastServer.id, lastServer.roomList[0].id));
      }
    });
  }

  onSettingsReceive(): void {
    this.router.navigateByUrl("settings");
  }

  onChangeServerReceive(server: Server): void {
    this.sss.setServerShared(new ServerSharedInfo(server.id, server.roomList[0].id));
  }

}
