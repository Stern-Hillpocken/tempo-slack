import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ServerSharedInfo } from '../core/models/server-shared-info.model';

@Injectable({
  providedIn: 'root'
})
export class ServerSharedService {

  private readonly _serverShared$: BehaviorSubject<ServerSharedInfo> = new BehaviorSubject<ServerSharedInfo>(new ServerSharedInfo(0,0));

  getServerShared(): Observable<ServerSharedInfo> {
    return this._serverShared$.asObservable();
  }

  setServerShared(ssi: ServerSharedInfo): void {
    console.log(ssi)
    this._serverShared$.next(ssi);
  }

  setServerId(serverId: number): void {
    let bhs: ServerSharedInfo = this._serverShared$.getValue();
    bhs.currentServerId = serverId;
    this.setServerShared(bhs);
  }

  setRoomId(roomId: number): void {
    let bhs: ServerSharedInfo = this._serverShared$.getValue();
    bhs.currentRoomId = roomId;
    this.setServerShared(bhs);
  }

  refresh(): void {
    this.setServerShared(this._serverShared$.getValue());
  }
}
