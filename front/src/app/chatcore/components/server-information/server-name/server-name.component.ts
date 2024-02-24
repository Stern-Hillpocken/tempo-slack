import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { LocalStorageService } from 'src/app/shared/local-storage.service';

@Component({
  selector: 'app-server-name',
  templateUrl: './server-name.component.html',
  styleUrls: ['./server-name.component.scss']
})
export class ServerNameComponent implements OnInit {
  id!: number;
  serverName!: string;
  editPopup: boolean = false; 
  formServer!: FormGroup; 

  constructor(
    private serverService: ServerService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private localStorage: LocalStorageService
  ) { }

  ngOnInit(): void {
    this.id = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.serverService.getServerById(this.id).subscribe(server => {
      this.serverName = server.name;
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
      this.serverService.updateServerNameById(this.id, this.formServer.get('serverName')?.value).subscribe(() => {
        this.serverName = this.formServer.get('serverName')?.value;
        this.editPopup = false; 
      });
    }
  }
}
