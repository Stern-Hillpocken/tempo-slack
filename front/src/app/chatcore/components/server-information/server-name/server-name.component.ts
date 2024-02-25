import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { ServerSharedService } from 'src/app/shared/servershared.service';

@Component({
  selector: 'app-server-name',
  templateUrl: './server-name.component.html',
  styleUrls: ['./server-name.component.scss']
})
export class ServerNameComponent implements OnInit {
  serverId!: number;
  serverName!: string;
  editPopup: boolean = false; 
  formServer!: FormGroup; 

  constructor(
    private serverService: ServerService,
    private fb: FormBuilder,
    private serverSharedService: ServerSharedService
  ) { }

  ngOnInit(): void {
    this.serverSharedService.currentServerId.pipe(
      switchMap((serverId: number | null) => {
        // Vérifie si l'ID est défini avant de continuer
        if (!serverId) return of(null); // Return un observable null si server id est null ou undefined
        this.serverId = serverId;
        console.log('serverId', serverId);
        // Ici, nous chaînons la requête GET après avoir reçu l'ID
        return this.serverService.getServerById(serverId);
      })
    ).subscribe(server => {
      // Cette partie s'exécute une fois que la requête GET est complétée
      if (server) {
        this.serverName = server.name;
        this.formServer.patchValue({
          serverName: this.serverName
        });
      }
    });

    this.formServer = this.fb.group({
      serverName: [this.serverName, [Validators.required, Validators.minLength(1)]],
    });
  }

  edit() {
    this.editPopup = true;
  }

  update() {
    if (this.formServer.valid) {
      this.serverService.updateServerNameById(this.serverId, this.formServer.get('serverName')?.value).subscribe(() => {
        this.serverName = this.formServer.get('serverName')?.value;
        this.editPopup = false; 
      });
    }
  }
}
