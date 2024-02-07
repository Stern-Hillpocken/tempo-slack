package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.*;
import firstgroup.temposlack.mapper.MessageMapper;
import firstgroup.temposlack.mapper.MessagePostedMapper;
import firstgroup.temposlack.mapper.RoomMapper;
import firstgroup.temposlack.mapper.ServerMapper;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.model.User;
import firstgroup.temposlack.service.MessageService;
import firstgroup.temposlack.service.RoomService;
import firstgroup.temposlack.service.ServerService;
import firstgroup.temposlack.service.UserService;
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

    //affiche tous les serveurs
    @GetMapping
    public List<Server> findAll() {
        return serverService.findAll();
    }

    //affiche 1 serveur selon id
    @GetMapping("{idServer}")
    public ResponseEntity<?> findById(@PathVariable("idServer") Long id) {
        Optional<Server> s = serverService.findById(id);
        if (s.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(s.get());
        }
    }

    //affiche 1 room selon id room et serveur
    @GetMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> findById(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom) {
        Optional<Server> s = serverService.findById(idServer);
        Optional<Room> r = roomService.getRoomById(idRoom);
        if (s.isEmpty() || r.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(r.get());
        }
    }

    //ajoute server et création room "général"
    @PostMapping
    public ResponseEntity<?> addServer(@RequestBody ServerDTO serverDTO) {
        Server server = ServerMapper.convertToEntity(serverDTO);
        server.addRoom(new Room("Général"));
        System.out.println(server.getRoomList());
        serverService.add(server);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    //ajoute room à un serveur selon son id
    @PostMapping("{idServer}")
    public ResponseEntity<?> addRoom(@PathVariable("idServer") Long idServer, @RequestBody RoomDTO roomDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        if (optionalServer.isEmpty() || roomDTO.getTitle().isBlank()) {
            return ResponseEntity.notFound().build();
        } else {
            Room room = RoomMapper.convertToEntity(roomDTO);
            roomService.createRoom(room);
            Server server = optionalServer.get();
            server.addRoom(room);
            serverService.add(server);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @PutMapping("{idServer}")
    public ResponseEntity<?> update(@PathVariable("idServer") Long id, @RequestBody Server server) {
        Optional<Server> s = serverService.findById(id);
        if (s.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (!id.equals(server.getId())) {
                return ResponseEntity.badRequest().build();
            } else {
                serverService.update(server);
                return ResponseEntity.ok(s.get());
            }
        }
    }

    @DeleteMapping("{idServer}")
    public ResponseEntity<?> delete(@PathVariable("idServer") Long id) {
        Optional<Server> c = serverService.findById(id);
        if (c.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            serverService.delete(id);
            return ResponseEntity.ok().build();
        }
    }

    //ajout message dans une room
    @PostMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> addMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody MessagePostedDTO messagePostedDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || messagePostedDTO == null || !serverService.isMessagePostedDTOValid(messagePostedDTO)) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            if (!server.isUserInServer(messagePostedDTO.getUser().getPseudo())) {
                return ResponseEntity.notFound().build();
            }
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList) {
                if (r.getId().equals(idRoom)) {
                    Message message = MessagePostedMapper.convertDTOtoEntity(messagePostedDTO);
                    Optional<User> optionalUser = userService.getByPseudo(messagePostedDTO.getUser().getPseudo());
                    if (optionalUser.isEmpty()) return ResponseEntity.notFound().build();
                    User user = optionalUser.get();
                    if (!user.getPassword().equals(messagePostedDTO.getUser().getPassword())) {
                        return ResponseEntity.notFound().build();
                    }
                    message.setUser(user);
                    roomService.addMessage(idRoom, message);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }
            }
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> updateRoom(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody RoomDTO roomDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        if (optionalServer.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList) {
                if (r.getId() == idRoom) {
                    Room room = RoomMapper.convertToEntity(roomDTO);
                    room.setId(idRoom);
                    roomService.updateRoom(room);
                }
            }
            return ResponseEntity.ok().build();
        }
    }


    @PutMapping("{idServer}/{idRoom}/{idMessage}")
    public ResponseEntity<?> editMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom,
                                         @PathVariable("idMessage") Long idMessage, @RequestBody MessagePostedDTO messagePostedDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<Message> optionalMessage = messageService.findById(idMessage);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalMessage.isEmpty() ||
                messagePostedDTO == null || !serverService.isMessagePostedDTOValid(messagePostedDTO)) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            if (!server.isUserInServer(messagePostedDTO.getUser().getPseudo())) {
                return ResponseEntity.notFound().build();
            }
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList) {
                if (r.getId().equals(idRoom)) {
                    Message messageEdit = MessagePostedMapper.convertDTOtoEntity(messagePostedDTO);
                    Message messageToUpdate = optionalMessage.get();
                    Optional<User> optionalUser = userService.getByPseudo(messagePostedDTO.getUser().getPseudo());
                    if (optionalUser.isEmpty()) return ResponseEntity.notFound().build();
                    User user = optionalUser.get();
                    if (!user.getPassword().equals(messagePostedDTO.getUser().getPassword())) {
                        return ResponseEntity.notFound().build();
                    }
                    messageToUpdate.setUser(user);
                    messageService.update(messageEdit, messageToUpdate);
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> deleteRoom(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        if (optionalServer.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList) {
                if (r.getId() == idRoom) {
                    roomService.deleteRoom(idRoom);
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{idServer}/{idRoom}/{idMessage}")
    public ResponseEntity<?> deleteMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom,
                                           @PathVariable("idMessage") Long idMessage, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<Message> optionalMessage = messageService.findById(idMessage);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalMessage.isEmpty() || userPseudoPasswordDTO == null ||
                !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO)) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
            if (optionalUser.isEmpty()) return ResponseEntity.notFound().build();
            User user = optionalUser.get();
            Message message = optionalMessage.get();
            if (!user.getPassword().equals(userPseudoPasswordDTO.getPassword()))
                return ResponseEntity.notFound().build();
            if (!user.equals(message.getUser())) return ResponseEntity.notFound().build();
            Room room = optionalRoom.get();
            messageService.delete(idMessage, room);
            return ResponseEntity.ok().build();
        }
    }
}
