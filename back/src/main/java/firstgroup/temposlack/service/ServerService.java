package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.ServerRepository;
import firstgroup.temposlack.dto.MessagePostedDTO;
import firstgroup.temposlack.dto.RoomCreatedDTO;
import firstgroup.temposlack.dto.ServerListDTO;
import firstgroup.temposlack.mapper.ServerListMapper;
import firstgroup.temposlack.model.Role;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
    @Autowired
    ServerRepository serverRepository;
    @Autowired
    RoleService roleService;

    public void createServer(Server server, User user) {
        Room room = new Room("Général");
        room.setRemovable(false);
        server.addRoom(room);
        server.addUser(user);
        Role role = new Role("owner");
        roleService.createRole(role, server);
        roleService.addRoleUser(role, user);
        serverRepository.save(server);
    }

    public List<Server> getAll() {
        return serverRepository.findAll();
    }

    public Optional<Server> getById(Long id) {
        return serverRepository.findById(id);
    }

    public void update(Server server) {
        serverRepository.save(server);
    }

    public void delete(Long id) {
        serverRepository.deleteById(id);
    }

    public boolean isMessagePostedDTOValid(MessagePostedDTO messagePostedDTO) {
        if (messagePostedDTO.getContent() == null || messagePostedDTO.getContent().isBlank() ||
                messagePostedDTO.getUser().getPseudo() == null || messagePostedDTO.getUser().getPseudo().isBlank() ||
                messagePostedDTO.getUser().getPassword() == null || messagePostedDTO.getUser().getPassword().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean isRoomCreatedDTOValid(RoomCreatedDTO roomCreatedDTO) {
        if (roomCreatedDTO.getTitle() == null || roomCreatedDTO.getTitle().isBlank() ||
                roomCreatedDTO.getUser().getPseudo() == null || roomCreatedDTO.getUser().getPseudo().isBlank() ||
                roomCreatedDTO.getUser().getPassword() == null || roomCreatedDTO.getUser().getPassword().isBlank()) {
            return false;
        }
        return true;
    }

    public List<ServerListDTO> getAllMine(User user) {
        List<Server> allServers = getAll();
        List<ServerListDTO> allMineServersDTOs = new ArrayList<>();

        for (Server server : allServers) {
            if (server.isUserInServer(user)) allMineServersDTOs.add(ServerListMapper.serverToServerListDTO(server));
        }
        return allMineServersDTOs;
    }
}
