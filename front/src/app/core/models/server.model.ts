// import { User } from "./user.model";
// import { Role } from "./role.model";
import { Room } from "./room.model";

export interface Server {
  id: number;
  name: string;
  roomList: Room[];
  //   userList: User[];
  //   roleList: Role[];
}
