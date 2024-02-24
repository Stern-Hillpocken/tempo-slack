import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Message} from "../../core/models/message";

@Injectable({
  providedIn: 'root'
})
export class MessagesService {
  apiUrl: string ='http://localhost:8080/servers'

  constructor(private http: HttpClient) { }

  
  getAllMessages(idServer : number, idRoom: number): Observable<Message[]> {
        return this.http.get<Message[]>(`${this.apiUrl}/1/1`);
   }

  
}
