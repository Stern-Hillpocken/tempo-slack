package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.MessageDTO;
import firstgroup.temposlack.dto.ServerDTO;
import firstgroup.temposlack.mapper.MessageMapper;
import firstgroup.temposlack.mapper.ServerMapper;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.Server;
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

    @GetMapping
    public List<Server> findAll() {
        return serverService.findAll();
    }

    //
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<Server> s = serverService.findById(id);
        if (s.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(s.get());
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ServerDTO serverDTO) {
        Server server = ServerMapper.convertToEntity(serverDTO);
        server.addRoom(new Room("Général"));
        System.out.println(server.getRoomList());
        serverService.add(server);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Server server) {
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

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Server> c = serverService.findById(id);
        if (c.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            serverService.delete(id);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("{idServer}/{idRoom}")
    public ResponseEntity<?> addMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody MessageDTO messageDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || messageDTO.getContent().isBlank()) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList) {
                if (r.getId().equals(idRoom)) {
                    Message message = MessageMapper.convertDTOtoEntity(messageDTO);
                    message.setUser(userService.getByPseudo(messageDTO.getUser()));
                    roomService.addMessage(idRoom, message);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }
            }
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{idServer}/{idRoom}/{idMessage}")
    public ResponseEntity<?> editMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom,
                                         @PathVariable("idMessage") Long idMessage, @RequestBody MessageDTO messageDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<Message> optionalMessage = messageService.findById(idMessage);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalMessage.isEmpty() || messageDTO.getContent().isBlank()) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList) {
                if (r.getId().equals(idRoom)) {
                    Message messageEdit = MessageMapper.convertDTOtoEntity(messageDTO);
                    Message messageToUpdate = optionalMessage.get();
                    messageToUpdate.setId(idMessage);
                    messageToUpdate.setUser(userService.getByPseudo(messageDTO.getUser()));
                    messageService.update(messageEdit, messageToUpdate);
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{idServer}/{idRoom}/{idMessage}")
    public ResponseEntity<?> deleteMessage(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom,
                                           @PathVariable("idMessage") Long idMessage) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<Room> optionalRoom = roomService.getRoomById(idRoom);
        Optional<Message> optionalMessage = messageService.findById(idMessage);
        if (optionalServer.isEmpty() || optionalRoom.isEmpty() || optionalMessage.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Room room = optionalRoom.get();
            messageService.delete(idMessage, room);
            return ResponseEntity.ok().build();
        }
    }
}
