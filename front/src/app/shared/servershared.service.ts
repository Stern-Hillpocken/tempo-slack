import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServerSharedService {
  private serverIdSource = new BehaviorSubject<number | null>(null);
  currentServerId = this.serverIdSource.asObservable();

  constructor() { }

  changeServerId(id: number) {
    this.serverIdSource.next(id);
  }
}
