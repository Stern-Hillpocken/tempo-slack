import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MessageComponent } from "./components/chat/message/message.component";
import { RoomNameComponent } from "./components/chat/room-name/room-name.component";
import { MessageFormComponent } from "./components/chat/message-form/message-form.component";
import { ServerDisplayComponent } from "./components/server-list/server-display/server-display.component";
import { ServerNameComponent } from "./components/server-information/server-name/server-name.component";
import { ServerRoomsComponent } from "./components/server-information/server-rooms/server-rooms.component";
import { ServerMembersComponent } from "./components/server-information/server-members/server-members.component";
import { ServerRolesComponent } from "./components/server-information/server-roles/server-roles.component";
import { HomeComponent } from "./pages/home/home.component";
import { ServerListComponent } from "./components/server-list/server-list.component";
import { ServerInformationComponent } from "./components/server-information/server-information.component";
import { ChatComponent } from "./components/chat/chat.component";
import { ChatcoreRoutingModule } from "./chatcore-routing.module";
import { ReactiveFormsModule } from "@angular/forms";
import { AddServerComponent } from "./components/server-list/add-server/add-server.component";
import { SelfSettingsComponent } from "./components/server-list/self-settings/self-settings.component";
import { UtilsModule } from "../utils/utils.module";
import { RoomDisplayComponent } from "./components/server-information/server-rooms/room-display/room-display.component";
import { AddRoomComponent } from "./components/server-information/server-rooms/add-room/add-room.component";
import { AddMembersComponent } from "./components/server-information/server-members/add-members/add-members.component";
import { DeleteMemberComponent } from "./components/server-information/server-members/delete-member/delete-member.component";
import { AddRoleComponent } from './components/server-information/server-roles/add-role/add-role.component';

@NgModule({
  declarations: [
    ServerListComponent,
    ServerInformationComponent,
    ChatComponent,
    MessageComponent,
    RoomNameComponent,
    MessageFormComponent,
    ServerDisplayComponent,
    ServerNameComponent,
    ServerRoomsComponent,
    ServerMembersComponent,
    ServerRolesComponent,
    HomeComponent,
    AddServerComponent,
    SelfSettingsComponent,
    RoomDisplayComponent,
    AddRoomComponent,
    AddMembersComponent,
    DeleteMemberComponent,
    AddRoleComponent,
  ],
  imports: [CommonModule, ChatcoreRoutingModule, ReactiveFormsModule, UtilsModule],
  exports: [
    ChatComponent,
    MessageComponent,
    MessageFormComponent,
    RoomNameComponent,
    ServerInformationComponent,
    ServerMembersComponent,
    ServerNameComponent,
    ServerRolesComponent,
    ServerRoomsComponent,
    ServerListComponent,
    ServerDisplayComponent,
  ],
})
export class ChatcoreModule {}
