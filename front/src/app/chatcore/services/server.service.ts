import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Server } from "src/app/core/models/server.model";
import { Room } from "src/app/core/models/room.model";
import { LocalStorageService } from "src/app/shared/local-storage.service";

@Injectable({
  providedIn: "root",
})
export class ServerService {
  apiUrl: string = "http://localhost:8080/servers";

  constructor(private http: HttpClient, private localStorageService: LocalStorageService) {}

  getServerById(idServer: number): Observable<Server> {
    return this.http.get<Server>(`${this.apiUrl}/${idServer}`);
  }

  getRoomInServerById(idServer: number, idRoom: number): Observable<Room> {
    return this.http.get<Room>(`${this.apiUrl}/${idServer}/${idRoom}`);
  }

  addRoomInServerById(roomTitle: string, idServer: number): Observable<Room> {
    let dto = { title: roomTitle, user: this.localStorageService.getPseudoPassword() };
    console.log(dto);
    return this.http.post<Room>(`${this.apiUrl}/${idServer}`, dto);
  }
}
