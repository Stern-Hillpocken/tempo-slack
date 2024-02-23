//import { Server } from "server.model";

export interface Room {
  id: number;
  title: string;
  userList: [];
  messageList:[];
  //server: Server;
}
