import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Server } from "src/app/core/models/server.model";
import { Room } from "src/app/core/models/room.model";
import { UtilsService } from "src/app/shared/utils.service";
import { PseudoPassword } from "src/app/core/models/pseudo-password.model";
import { LocalStorageService } from "src/app/shared/local-storage.service";
import { Message } from "src/app/core/models/message";
import { MessageSended } from "src/app/core/models/message-sended";
import { User } from "src/app/core/models/user.model";

@Injectable({
  providedIn: "root",
})
export class ServerService {
  constructor(
    private http: HttpClient,
    private utils: UtilsService,
    private localStorageService: LocalStorageService
  ) {}

  getServerById(id: number): Observable<Server> {
    return this.http.get<Server>(this.utils.getBaseUrl() + "servers/" + id);
  }

  getRoomInServerById(idServer: number, idRoom: number): Observable<Room> {
    return this.http.get<Room>(this.utils.getBaseUrl() + `servers/${idServer}/${idRoom}`);
  }

  getServersOfUser(pp: PseudoPassword): Observable<Server[]> {
    return this.http.post<Server[]>(this.utils.getBaseUrl() + "servers/mine", pp);
  }

  addServer(serverCreatedDTO: { name: string; user: PseudoPassword }): Observable<any> {
    return this.http.post<any>(this.utils.getBaseUrl() + "servers", serverCreatedDTO);
  }

  addRoom(
    idServer: number,
    roomTitle: string,
    roomCreatedDTO: { title: string; user: PseudoPassword }
  ): Observable<any> {
    return this.http.post<Room>(this.utils.getBaseUrl() + "servers/" + idServer, roomCreatedDTO);
  }

  addUser(idServer: number, userAddedToServerDTO: { userPseudoToAdd: string; user: PseudoPassword }): Observable<any> {
    return this.http.post<User>(this.utils.getBaseUrl() + "servers/" + idServer, userAddedToServerDTO);
  }

  updateRoomName(
    idServer: number,
    idRoom: number,
    roomTitle: string,
    roomCreatedDTO: { title: string; user: PseudoPassword }
  ): Observable<any> {
    return this.http.put<Room>(this.utils.getBaseUrl() + "servers/" + idServer + idRoom, roomCreatedDTO);
  }

  updateServerNameById(idServer: number, updateName: string): Observable<any> {
    let dto = { name: updateName, user: this.localStorageService.getPseudoPassword() };
    console.log(dto);

    return this.http.put<any>(this.utils.getBaseUrl() + "servers/" + idServer, dto);
  }

  addMessage(message: MessageSended, idServer: number, idRoom: number): Observable<Message> {
    console.log(message);
    console.log("idServ: " + idServer + " idRoom: " + idRoom);
    return this.http.post<Message>(this.utils.getBaseUrl() + "servers/" + idServer + "/" + idRoom, message);
  }
  deleteMessageInRoomInServerById(
    idServer: number,
    idRoom: number,
    idMessage: number,
    user: PseudoPassword
  ): Observable<Message> {
    return this.http.post<Message>(
      this.utils.getBaseUrl() + "servers/" + idServer + "/" + idRoom + "/" + idMessage,
      user
    );
  }

  updateMessage(idMessage: number, message: MessageSended): Observable<Message> {
    return this.http.put<Message>(this.utils.getBaseUrl() + "servers/edit-message/" + idMessage, message);
  }

  deleteRoomInServerById(idServer: number, idRoom: number, user: PseudoPassword): Observable<any> {
    return this.http.post<any>(this.utils.getBaseUrl() + "servers/delete-room/" + idServer + "/" + idRoom, user);
  }

  updateRoom(idServer: number, idRoom: number, room: string): Observable<any> {
    let dto = { user: this.localStorageService.getPseudoPassword(), title: room };
    return this.http.put<any>(this.utils.getBaseUrl() + "servers/" + idServer + "/" + idRoom, dto);
  }
}
