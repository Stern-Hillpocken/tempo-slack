import { Component } from '@angular/core';
import { Server } from 'src/app/core/models/server.model';
import { ServerService } from '../../services/server.service';
import { LocalStorageService } from 'src/app/shared/local-storage.service';

@Component({
  selector: 'app-server-list',
  templateUrl: './server-list.component.html',
  styleUrls: ['./server-list.component.scss']
})
export class ServerListComponent {

  servers!: Server[];

  constructor(
    private serverService: ServerService,
    private lss: LocalStorageService
  ){}

  ngOnInit(): void {
    this.serverService.getServersOfUser(this.lss.getPseudoPassword()).subscribe(servers => {
      this.servers = servers;
    });
  }

}
