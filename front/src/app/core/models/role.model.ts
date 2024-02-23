import { Server } from "./server.model";
import { User } from "./user.model";

export interface Role {
  id: number;
  name: string;
  userList: User[];
  server: Server;
}
