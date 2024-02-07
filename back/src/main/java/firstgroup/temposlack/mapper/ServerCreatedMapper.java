package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.ServerCreatedDTO;
import firstgroup.temposlack.dto.ServerDTO;
import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.model.User;

public class ServerCreatedMapper {

    public static ServerCreatedDTO convertToDTO(Server server){
    ServerCreatedDTO dto = new ServerCreatedDTO();
    dto.setName(server.getName());
    dto.setRoomList(server.getRoomList());
    dto.setUserList(server.getUserList());
    return dto;
    }

    public static Server convertToEntity(ServerCreatedDTO serverCreatedDTO){
        Server  entity = new Server();
        entity.setName(serverCreatedDTO.getName());
        if (serverCreatedDTO.getRoomList() != null){
            entity.setRoomList(serverCreatedDTO.getRoomList());
        }
       if (serverCreatedDTO.getUserList() != null){
           entity.setUserList(serverCreatedDTO.getUserList());
       }

       return entity;
    }
}
