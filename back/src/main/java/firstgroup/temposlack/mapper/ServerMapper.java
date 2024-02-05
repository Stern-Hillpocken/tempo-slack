package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.ServerDTO;
import firstgroup.temposlack.model.Server;

public class ServerMapper {

    public static ServerDTO convertToDTO(Server server){
    ServerDTO dto = new ServerDTO();
    dto.setName(server.getName());
    dto.setRoomList(server.getRoomList());
    return dto;
    }

    public static Server convertToEntity(ServerDTO serverDTO){
        Server  entity = new Server();
        entity.setName(serverDTO.getName());
        entity.setRoomList(serverDTO.getRoomList());
        return entity;
    }
}
