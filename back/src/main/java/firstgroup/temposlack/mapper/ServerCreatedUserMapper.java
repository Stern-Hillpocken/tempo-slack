package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.ServerCreatedDTO;
import firstgroup.temposlack.dto.ServerCreatedUserDTO;
import firstgroup.temposlack.model.Server;

public class ServerCreatedUserMapper {

    public static ServerCreatedUserDTO convertToDTO(Server server) {
        ServerCreatedUserDTO dto = new ServerCreatedUserDTO();
        dto.setName(server.getName());
        dto.setRoomList(server.getRoomList());
        dto.setUserList(server.getUserList());
        return dto;
    }

    public static Server convertToEntity(ServerCreatedUserDTO serverCreatedUserDTO) {
        Server entity = new Server();
        entity.setName(serverCreatedUserDTO.getName());
        if (serverCreatedUserDTO.getRoomList() != null) {
            entity.setRoomList(serverCreatedUserDTO.getRoomList());
        }
        if (serverCreatedUserDTO.getUserList() != null) {
            entity.setUserList(serverCreatedUserDTO.getUserList());
        }
        return entity;
    }
}
