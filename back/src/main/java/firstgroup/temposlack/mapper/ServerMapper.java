package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.ServerDTO;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.Server;

public class ServerMapper {

    public static ServerDTO convertToDTO(Server server) {
        ServerDTO dto = new ServerDTO();
        dto.setName(server.getName());
        dto.setRoomList(server.getRoomList());
        dto.setUserList(server.getUserList());
        return dto;
    }

    public static Server convertToEntity(ServerDTO serverDTO) {
        Server entity = new Server();
        entity.setName(serverDTO.getName());
        if (serverDTO.getRoomList() != null) {
            entity.setRoomList(serverDTO.getRoomList());
        }
        if (serverDTO.getUserList() != null) {
            entity.setUserList(serverDTO.getUserList());
        }
        return entity;
    }
}
