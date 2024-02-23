import { Server } from "./server.model";
import { User } from "./user.model";

export interface Room {
  id: number;
  title: string;
  userList: User[];
  server: Server;
}
