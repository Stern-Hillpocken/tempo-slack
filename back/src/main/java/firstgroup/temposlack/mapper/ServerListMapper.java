package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.ServerListDTO;
import firstgroup.temposlack.model.Server;

public class ServerListMapper {

    public static ServerListDTO serverToServerListDTO(Server server) {
        return new ServerListDTO(server.getId(), server.getName());
    }
}
