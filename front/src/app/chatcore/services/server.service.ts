import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Server } from "src/app/core/models/server.model";
import { Room } from "src/app/core/models/room.model";
import { UtilsService } from "src/app/shared/utils.service.";
import { PseudoPassword } from "src/app/core/models/pseudo-password.model";

@Injectable({
  providedIn: "root",
})
export class ServerService {

  constructor(
    private http: HttpClient,
    private utils: UtilsService
    ) {}

  getServerById(id: number): Observable<Server> {
    return this.http.get<Server>(this.utils.getBaseUrl()+"servers/"+id);
  }

  getRoomInServerById(idServer: number, idRoom: number): Observable<Room> {
    return this.http.get<Room>(this.utils.getBaseUrl()+`servers/${idServer}/${idRoom}`);
  }

  getServersOfUser(pp: PseudoPassword): Observable<Server[]> {
    return this.http.post<Server[]>(this.utils.getBaseUrl()+"servers/mine", pp);
  }
}
