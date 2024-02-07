package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.*;
import firstgroup.temposlack.mapper.*;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.model.User;
import firstgroup.temposlack.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("servers")
public class ServerController {
    @Autowired
    ServerService serverService;
    @Autowired
    RoomService roomService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    RoleService roleService;

    //affiche tous les serveurs
    @GetMapping
    public ResponseEntity<List<Server>> findAll() {
        return ResponseEntity.ok(serverService.findAll());
    }

    //affiche 1 serveur selon id
    @GetMapping("{idServer}")
    public ResponseEntity<?> findById(@PathVariable("idServer") Long id) {
        Optional<Server> optionalServer = serverService.findById(id);
        if (optionalServer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalServer.get());
    }

    //affiche 1 room selon id room et serveur
    @GetMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> findById(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalRoom.get());
    }

    //ajoute server et création room "général"
    @PostMapping
    public ResponseEntity<?> addServer(@RequestBody ServerCreatedDTO serverCreatedDTO) {
        if (serverCreatedDTO == null || !userService.isUserPseudoPasswordDTOValid(serverCreatedDTO.getUser()) ||
                serverCreatedDTO.getName() == null || serverCreatedDTO.getName().isBlank())
            return ResponseEntity.noContent().build();
        if (!userService.isUserMatching(serverCreatedDTO.getUser())) return ResponseEntity.notFound().build();
        Optional<User> optionalUser = userService.getByPseudo(serverCreatedDTO.getUser().getPseudo());
        if (optionalUser.isEmpty()) return ResponseEntity.notFound().build();
        Server server = ServerCreatedMapper.convertToEntity(serverCreatedDTO);
        User user = optionalUser.get();
        serverService.add(server, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    // ajoute un user à un serveur
    @PostMapping("{idServer}/add-user")
    public ResponseEntity<?> addUserToServer(@PathVariable("idServer") Long idServer,
                                             @RequestBody UserAddedToServerDTO userAddedToServerDTO) {
        if (userAddedToServerDTO == null || !userService.isUserPseudoPasswordDTOValid(userAddedToServerDTO.getUser()) || userAddedToServerDTO.getUserPseudoToAdd() == null || userAddedToServerDTO.getUserPseudoToAdd().isBlank())
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<User> optionalUser = userService.getByPseudo(userAddedToServerDTO.getUser().getPseudo());
        Optional<User> optionalUserToAdd = userService.getByPseudo(userAddedToServerDTO.getUserPseudoToAdd());
        if (optionalServer.isEmpty() || optionalUser.isEmpty() || optionalUserToAdd.isEmpty())
            return ResponseEntity.notFound().build();
        if (!userService.isUserMatching(userAddedToServerDTO.getUser())) return ResponseEntity.notFound().build();

        Server server = optionalServer.get();
        User user = optionalUser.get();
        User userToAdd = optionalUserToAdd.get();

        // if user asking is in server
        if (!server.isUserInServer(user) || !roleService.isOwner(user, server))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        server.addUser(userToAdd);
        serverService.update(server);
        return ResponseEntity.ok().build();
    }

    //ajoute room à un serveur selon son id
    @PostMapping("{idServer}")
    public ResponseEntity<?> addRoom(@PathVariable("idServer") Long idServer, @RequestBody RoomCreatedDTO roomCreatedDTO) {
        if (roomCreatedDTO == null || roomCreatedDTO.getTitle() == null || roomCreatedDTO.getTitle().isBlank() || !serverService.isRoomCreatedDTOValid(roomCreatedDTO))
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<User> optionalUser = userService.getByPseudo(roomCreatedDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();

        Room room = RoomCreatedMapper.convertDTOToEntity(roomCreatedDTO);
        Server server = optionalServer.get();
        User user = optionalUser.get();

        if (!server.isUserInServer(user)) return ResponseEntity.notFound().build();
        roomService.createRoom(room, server);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{idServer}")
    public ResponseEntity<?> update(@PathVariable("idServer") Long id, @RequestBody ServerUpdateDTO serverUpdateDTO) {
        if (serverUpdateDTO == null || !userService.isUserPseudoPasswordDTOValid(serverUpdateDTO.getUser()) || serverUpdateDTO.getName() == null || serverUpdateDTO.getName().isBlank())
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.findById(id);
        Optional<User> optionalUser = userService.getByPseudo(serverUpdateDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();

        if (!userService.isUserMatching(serverUpdateDTO.getUser())) return ResponseEntity.notFound().build();

        Server server = optionalServer.get();
        User user = optionalUser.get();

        if (!server.isUserInServer(user) || !roleService.isOwner(user, server))
            return ResponseEntity.notFound().build();

        serverService.update(server);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{idServer}")
    public ResponseEntity<?> delete(@PathVariable("idServer") Long id, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.findById(id);
        Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();
        if (!userService.isUserMatching(userPseudoPasswordDTO)) return ResponseEntity.notFound().build();

        User user = optionalUser.get();
        Server server = optionalServer.get();

        if (!server.isUserInServer(user) || !roleService.isOwner(user, server))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        serverService.delete(id);
        return ResponseEntity.ok().build();

    }

    //ajout message dans une room
    @PostMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> addMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody MessagePostedDTO messagePostedDTO) {
        if (messagePostedDTO == null || !serverService.isMessagePostedDTOValid(messagePostedDTO) || messagePostedDTO.getContent() == null || messagePostedDTO.getContent().isBlank())
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<User> optionalUser = userService.getByPseudo(messagePostedDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalUser.isEmpty())
            return ResponseEntity.notFound().build();

        Server server = optionalServer.get();
        User user = optionalUser.get();
        if (!server.isUserInServer(user)) return ResponseEntity.notFound().build();
        if (!userService.isUserMatching(messagePostedDTO.getUser())) return ResponseEntity.notFound().build();

        List<Room> roomList = server.getRoomList();
        for (Room r : roomList) {
            if (r.getId().equals(idRoom)) {
                Message message = MessagePostedMapper.convertDTOtoEntity(messagePostedDTO);
                message.setUser(user);
                roomService.addMessage(idRoom, message);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    // modifie une room
    @PutMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> updateRoom(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody RoomCreatedDTO roomCreatedDTO) {
        if (roomCreatedDTO == null || roomCreatedDTO.getUser() == null || roomCreatedDTO.getTitle() == null ||
                roomCreatedDTO.getTitle().isBlank() || !userService.isUserPseudoPasswordDTOValid(roomCreatedDTO.getUser()))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<User> optionalUser = userService.getByPseudo(roomCreatedDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            User user = optionalUser.get();
            if (!server.isUserInServer(user)) {
                return ResponseEntity.notFound().build();
            }
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList) {
                if (r.getId().equals(idRoom)) {
                    Room room = RoomCreatedMapper.convertDTOToEntity(roomCreatedDTO);
                    room.setId(idRoom);
                    roomService.updateRoom(room);
                }
            }
            return ResponseEntity.ok().build();
        }
    }


    @PutMapping("edit-message/{idMessage}")
    public ResponseEntity<?> editMessage(@PathVariable("idMessage") Long idMessage, @RequestBody MessagePostedDTO messagePostedDTO) {
        if (messagePostedDTO == null || !userService.isUserPseudoPasswordDTOValid(messagePostedDTO.getUser()) || messagePostedDTO.getContent() == null || messagePostedDTO.getContent().isBlank())
            return ResponseEntity.noContent().build();
        Optional<User> optionalUser = userService.getByPseudo(messagePostedDTO.getUser().getPseudo());
        Optional<Message> optionalMessage = messageService.findById(idMessage);
        if (optionalMessage.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();

        User user = optionalUser.get();
        Message message = optionalMessage.get();

        if (!message.getUser().equals(user)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        message.setContent(messagePostedDTO.getContent());
        messageService.update(message);
        return ResponseEntity.ok().build();
    }

    // supprime une room
    @DeleteMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> deleteRoom(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalUser.isEmpty())
            return ResponseEntity.notFound().build();
        if (!userService.isUserMatching(userPseudoPasswordDTO))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Server server = optionalServer.get();
        User user = optionalUser.get();
        Room room = optionalRoom.get();
        if (!room.isRemovable()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (!server.isUserInServer(user)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Room> roomList = server.getRoomList();
        for (Room r : roomList) {
            if (r.getId().equals(idRoom)) {
                roomService.deleteRoom(idRoom);
                server.deleteRoom(optionalRoom.get());
                serverService.update(server);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("{idServer}/{idRoom}/{idMessage}")
    public ResponseEntity<?> deleteMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom,
                                           @PathVariable("idMessage") Long idMessage, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<Message> optionalMessage = messageService.findById(idMessage);
        Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalMessage.isEmpty() || optionalUser.isEmpty())
            return ResponseEntity.notFound().build();

        User user = optionalUser.get();
        Room room = optionalRoom.get();
        Message message = optionalMessage.get();

        if (!user.getPassword().equals(userPseudoPasswordDTO.getPassword()))
            return ResponseEntity.notFound().build();
        if (!user.equals(message.getUser())) return ResponseEntity.notFound().build();

        messageService.delete(idMessage, room);
        return ResponseEntity.ok().build();
    }
}
