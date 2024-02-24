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

    //Get all servers
    @GetMapping
    public ResponseEntity<List<Server>> findAll() {
        return ResponseEntity.ok(serverService.getAll());
    }

    //Get one server
    @GetMapping("{idServer}")
    public ResponseEntity<?> findById(@PathVariable("idServer") Long id) {
        Optional<Server> optionalServer = serverService.getById(id);
        if (optionalServer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalServer.get());
    }

    //Get one room in a server
    @GetMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> findById(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom) {
        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(optionalRoom.get());
    }

    //Post a server and create room "général"
    @PostMapping
    public ResponseEntity<?> addServer(@RequestBody ServerCreatedDTO serverCreatedDTO) {
        if (serverCreatedDTO == null || !userService.isUserPseudoPasswordDTOValid(serverCreatedDTO.getUser()) ||
                serverCreatedDTO.getName() == null || serverCreatedDTO.getName().isBlank())
            return ResponseEntity.noContent().build();
        if (!userService.isUserPasswordMatching(serverCreatedDTO.getUser())) return ResponseEntity.notFound().build();
        Optional<User> optionalUser = userService.getByPseudo(serverCreatedDTO.getUser().getPseudo());
        if (optionalUser.isEmpty()) return ResponseEntity.notFound().build();
        Server server = ServerCreatedMapper.convertToEntity(serverCreatedDTO);
        User user = optionalUser.get();
        serverService.createServer(server, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("mine")
    public ResponseEntity<?> allMine(@RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO)) return ResponseEntity.noContent().build();
        if (!userService.isUserPasswordMatching(userPseudoPasswordDTO)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userService.getByPseudo(userPseudoPasswordDTO.getPseudo()).get();
        List<ServerListDTO> serverListDTOs = serverService.getAllMine(user);
        return ResponseEntity.ok(serverListDTOs);
    }

    // Add a user to a server
    @PostMapping("{idServer}/add-user")
    public ResponseEntity<?> addUserToServer(@PathVariable("idServer") Long idServer,
                                             @RequestBody UserAddedToServerDTO userAddedToServerDTO) {
        if (userAddedToServerDTO == null || !userService.isUserPseudoPasswordDTOValid(userAddedToServerDTO.getUser()) || userAddedToServerDTO.getUserPseudoToAdd() == null || userAddedToServerDTO.getUserPseudoToAdd().isBlank())
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<User> optionalUser = userService.getByPseudo(userAddedToServerDTO.getUser().getPseudo());
        Optional<User> optionalUserToAdd = userService.getByPseudo(userAddedToServerDTO.getUserPseudoToAdd());
        if (optionalServer.isEmpty() || optionalUser.isEmpty() || optionalUserToAdd.isEmpty())
            return ResponseEntity.notFound().build();
        if (!userService.isUserPasswordMatching(userAddedToServerDTO.getUser())) return ResponseEntity.notFound().build();

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

    //Add a room to a server
    @PostMapping("{idServer}")
    public ResponseEntity<?> addRoom(@PathVariable("idServer") Long idServer, @RequestBody RoomCreatedDTO roomCreatedDTO) {
        if (roomCreatedDTO == null || roomCreatedDTO.getTitle() == null || roomCreatedDTO.getTitle().isBlank() || !serverService.isRoomCreatedDTOValid(roomCreatedDTO))
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<User> optionalUser = userService.getByPseudo(roomCreatedDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();

        Room room = RoomCreatedMapper.convertDTOToEntity(roomCreatedDTO);
        Server server = optionalServer.get();
        User user = optionalUser.get();

        if (!server.isUserInServer(user)) return ResponseEntity.notFound().build();
        roomService.createRoom(room, server);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //update name server
    @PutMapping("{idServer}")
    public ResponseEntity<?> renameServer(@PathVariable("idServer") Long id, @RequestBody ServerUpdateDTO serverUpdateDTO) {
        if (serverUpdateDTO == null || !userService.isUserPseudoPasswordDTOValid(serverUpdateDTO.getUser()) || serverUpdateDTO.getName() == null || serverUpdateDTO.getName().isBlank())
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.getById(id);
        Optional<User> optionalUser = userService.getByPseudo(serverUpdateDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();

        if (!userService.isUserPasswordMatching(serverUpdateDTO.getUser())) return ResponseEntity.notFound().build();

        Server server = optionalServer.get();
        User user = optionalUser.get();

        if (!server.isUserInServer(user) || !roleService.isOwner(user, server))
            return ResponseEntity.notFound().build();
        server.setName(serverUpdateDTO.getName());
        serverService.update(server);
        return ResponseEntity.ok().build();
    }

    //Delete a server
    @DeleteMapping("{idServer}")
    public ResponseEntity<?> delete(@PathVariable("idServer") Long id, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.getById(id);
        Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();
        if (!userService.isUserPasswordMatching(userPseudoPasswordDTO)) return ResponseEntity.notFound().build();

        User user = optionalUser.get();
        Server server = optionalServer.get();

        if (!server.isUserInServer(user) || !roleService.isOwner(user, server))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        serverService.delete(id);
        return ResponseEntity.ok().build();
    }

    //Add message in a room
    @PostMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> addMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody MessagePostedDTO messagePostedDTO) {
        if (messagePostedDTO == null || !serverService.isMessagePostedDTOValid(messagePostedDTO) || messagePostedDTO.getContent() == null || messagePostedDTO.getContent().isBlank())
            return ResponseEntity.noContent().build();

        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<User> optionalUser = userService.getByPseudo(messagePostedDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalUser.isEmpty())
            return ResponseEntity.notFound().build();

        Server server = optionalServer.get();
        User user = optionalUser.get();
        if (!server.isUserInServer(user)) return ResponseEntity.notFound().build();
        if (!userService.isUserPasswordMatching(messagePostedDTO.getUser())) return ResponseEntity.notFound().build();

        List<Room> roomList = server.getRoomList();
        for (Room r : roomList) {
            if (r.getId().equals(idRoom)) {
                Message message = MessagePostedMapper.convertDTOtoEntity(messagePostedDTO);
                message.setUser(user);
                roomService.addMessage(r, message);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    // Update a room
    @PutMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> updateRoom(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody RoomCreatedDTO roomCreatedDTO) {
        if (roomCreatedDTO == null || roomCreatedDTO.getUser() == null || roomCreatedDTO.getTitle() == null ||
                roomCreatedDTO.getTitle().isBlank() || !userService.isUserPseudoPasswordDTOValid(roomCreatedDTO.getUser()))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.getById(idServer);
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

    // Update a message
    @PutMapping("edit-message/{idMessage}")
    public ResponseEntity<?> editMessage(@PathVariable("idMessage") Long idMessage, @RequestBody MessagePostedDTO messagePostedDTO) {
        if (messagePostedDTO == null || !userService.isUserPseudoPasswordDTOValid(messagePostedDTO.getUser()) || messagePostedDTO.getContent() == null || messagePostedDTO.getContent().isBlank())
            return ResponseEntity.noContent().build();
        Optional<User> optionalUser = userService.getByPseudo(messagePostedDTO.getUser().getPseudo());
        Optional<Message> optionalMessage = messageService.getById(idMessage);
        if (optionalMessage.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();

        User user = optionalUser.get();
        Message message = optionalMessage.get();

        if (!message.getUser().equals(user)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        message.setContent(messagePostedDTO.getContent());
        messageService.update(message);
        return ResponseEntity.ok().build();
    }

    // Delete a room
    @DeleteMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> deleteRoom(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalUser.isEmpty())
            return ResponseEntity.notFound().build();
        if (!userService.isUserPasswordMatching(userPseudoPasswordDTO))
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

    //delete message
    @DeleteMapping("{idServer}/{idRoom}/{idMessage}")
    public ResponseEntity<?> deleteMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom,
                                           @PathVariable("idMessage") Long idMessage, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();
        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<Message> optionalMessage = messageService.getById(idMessage);
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

    @DeleteMapping("{idServer}/remove-user")
    public ResponseEntity<?> removeUserFromServer(@PathVariable Long idServer, @RequestBody UserToUpdateInServerDTO userToUpdateInServerDTO) {
        if (userToUpdateInServerDTO.getUser() == null || !userService.isUserPseudoPasswordDTOValid(userToUpdateInServerDTO.getUser())) return ResponseEntity.noContent().build();
        if (userToUpdateInServerDTO.getUserPseudoToUpdate() == null || userToUpdateInServerDTO.getUserPseudoToUpdate().isBlank()) return ResponseEntity.noContent().build();

        if (userService.getByPseudo(userToUpdateInServerDTO.getUserPseudoToUpdate()).isEmpty()) return ResponseEntity.notFound().build();
        User userToUpdate = userService.getByPseudo(userToUpdateInServerDTO.getUserPseudoToUpdate()).get();
        if (!userService.isUserPasswordMatching(userToUpdateInServerDTO.getUser())) return ResponseEntity.notFound().build();
        User user = userService.getByPseudo(userToUpdateInServerDTO.getUser().getPseudo()).get();

        Optional<Server> optionalServer = serverService.getById(idServer);
        if (optionalServer.isEmpty()) return ResponseEntity.notFound().build();
        Server server = optionalServer.get();


        if (!server.isUserInServer(user) || !server.isUserInServer(userToUpdate)) return ResponseEntity.notFound().build();
        if (!roleService.isOwner(user, server) && !user.equals(userToUpdate)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        server.removeUser(userToUpdate);
        serverService.update(server);

        return ResponseEntity.ok().build();
    }
}
