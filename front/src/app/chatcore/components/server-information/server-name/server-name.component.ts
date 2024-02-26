import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { ServerSharedService } from 'src/app/shared/server-shared.service';

@Component({
  selector: 'app-server-name',
  templateUrl: './server-name.component.html',
  styleUrls: ['./server-name.component.scss']
})
export class ServerNameComponent implements OnInit {
  serverId!: number;
  serverName!: string;
  isPopupDisplay: boolean = false;
  formServer!: FormGroup; 

  constructor(
    private serverService: ServerService,
    private fb: FormBuilder,
    private serverSharedService: ServerSharedService
  ) { }

  ngOnInit(): void {
    this.formServer = this.fb.group({
      serverName: ['', [Validators.required, Validators.minLength(1)]],
    });

    this.serverSharedService.getServerShared().subscribe(serverInfo => {
      this.serverId = serverInfo.currentServerId;
      if (this.serverId) {
        this.serverService.getServerById(this.serverId).subscribe({
          next: (server) => {
            this.serverName = server.name;
            this.formServer.patchValue({ serverName: this.serverName });
          },
        });
      }
    });
  }

  openEditPopup(): void {
    this.isPopupDisplay = true;
  }

  closeEditPopup(): void {
    this.isPopupDisplay = false;
  }

  updateServerName(): void {
    if (this.formServer.valid) {
      this.serverService.updateServerNameById(this.serverId, this.formServer.get('serverName')?.value)
        .subscribe({
          next: () => {
            this.serverName = this.formServer.get('serverName')?.value;
            this.closeEditPopup();
          },
        });
    }
  }
}
