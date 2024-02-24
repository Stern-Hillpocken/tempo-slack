import { Component, Input } from '@angular/core';
import { Server } from 'src/app/core/models/server.model';

@Component({
  selector: 'app-server-display',
  templateUrl: './server-display.component.html',
  styleUrls: ['./server-display.component.scss']
})
export class ServerDisplayComponent {

  @Input()
  server!: Server;

}
