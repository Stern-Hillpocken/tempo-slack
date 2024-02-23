import { Server } from "./server.model";

export interface User {
  id: number;
  pseudo: string;
  password: string;
  email: string;
  avatar: string;
  lastUpdate: Date;
  serverList: Server[];
  accountIsActive: boolean;
}
