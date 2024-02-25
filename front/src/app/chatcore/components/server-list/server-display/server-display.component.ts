import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Server } from 'src/app/core/models/server.model';

@Component({
  selector: 'app-server-display',
  templateUrl: './server-display.component.html',
  styleUrls: ['./server-display.component.scss']
})
export class ServerDisplayComponent {

  @Input()
  currentServerId!: number;

  @Input()
  server!: Server;

  @Output()
  changeServerEmitter: EventEmitter<Server> = new EventEmitter();

  changeServer(): void {
    this.changeServerEmitter.emit(this.server);
  }

}
