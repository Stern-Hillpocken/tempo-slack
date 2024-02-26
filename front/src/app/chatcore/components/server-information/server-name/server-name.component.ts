import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { PopupFeedback } from 'src/app/core/models/popup-feedback.model';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
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
  isDeletePopupDisplay: boolean = false;

  constructor(
    private serverService: ServerService,
    private fb: FormBuilder,
    private serverSharedService: ServerSharedService,
    private pfs: PopupFeedbackService
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
      const newName = this.formServer.get('serverName')?.value;
      this.serverService.updateServerNameById(this.serverId, newName).subscribe(() => {
        this.serverSharedService.updateServerName(newName); 
        this.closeEditPopup();
      });
    }
  }

  openDeletePopup(): void {
    this.isDeletePopupDisplay = true;
  }

  closeDeletePopup(): void {
    this.isDeletePopupDisplay = false;
  }

  deleteServer(): void {
    this.serverService.deleteServer(this.serverId).subscribe(resp => {
      this.pfs.setFeed(new PopupFeedback("Serveur bien supprimé ", "valid"));
      this.serverSharedService.refresh();
    });
  }
}
  
    
  

