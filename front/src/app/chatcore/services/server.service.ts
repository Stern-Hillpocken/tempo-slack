import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Server } from "src/app/core/models/server.model";
import { Room } from "src/app/core/models/room.model";

@Injectable({
  providedIn: "root",
})
export class ServerService {
  apiUrl: string = "http://localhost:8080/servers";

  constructor(private http: HttpClient) {}

  getServerById(id: number): Observable<Server> {
    return this.http.get<Server>(`${this.apiUrl}/${id}`);
  }

  getRoomInServerById(idServer: number, idRoom: number): Observable<Room> {
    return this.http.get<Room>(`${this.apiUrl}/${idServer}/${idRoom}`);
  }
}