import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Message} from "../../core/models/message";

@Injectable({
  providedIn: 'root'
})
export class MessagesService {
  apiUrl: string ='http://localhost:8080/servers/'

  constructor(private http: HttpClient) { }

  addMessage(message: Message): Observable<Message>{
         return this.http.post<Message>(`${this.apiUrl}1/1`, message)
  }

  getAllMessages(): Observable<Message[]> {
        return this.http.get<Message[]>(`${this.apiUrl}1/1`);
   }

//   

//   deleteClient(id: number): Observable<Client>{
//     return this.http.delete<Client>(`${this.apiUrl}/${id}`)
//   }

//   getClientById(id : number): Observable<Client>{
//     return this.http.get<Client>(`${this.apiUrl}/${id}`)
//   }

//   updateClient(client : Client): Observable<Client>{
//     return this.http.put<Client>(`${this.apiUrl}/${client.id}`, client)
//   }

  
  
}
