package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.MessageDTO;
import firstgroup.temposlack.dto.RoomDTO;
import firstgroup.temposlack.dto.ServerDTO;
import firstgroup.temposlack.mapper.MessageMapper;
import firstgroup.temposlack.mapper.RoomMapper;
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
            for (Room r : roomList){
                if (r.getId() == idRoom){
                    Message message = MessageMapper.convertDTOtoEntity(messageDTO);
                    message.setUser(userService.getByPseudo(messageDTO.getUser()));
                    roomService.addMessage(idRoom,message);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                }
            }
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{idServer}")
    public ResponseEntity<?> addRoom(@PathVariable("idServer") Long idServer,@RequestBody RoomDTO roomDTO) {
        Room room = RoomMapper.convertToEntity(roomDTO);
        roomService.createRoom(room);
        Optional<Server> optionalServer = serverService.findById(idServer);
        if (optionalServer.isEmpty())
            return ResponseEntity.notFound().build();
        Server server = optionalServer.get();
        server.addRoom(room);
        serverService.add(server);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
@PutMapping("{idServer}{idRoom}")
    public ResponseEntity<?> updateRoom(@PathVariable("idServer") Long idServer, @PathVariable("idRoom") Long idRoom, @RequestBody RoomDTO roomDTO) {
        Optional<Server> optionalServer = serverService.findById(idServer);
        if (optionalServer.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Server server = optionalServer.get();
            List<Room> roomList = server.getRoomList();
            for (Room r : roomList){
                if (r.getId() == idRoom){
                    Room room = RoomMapper.convertToEntity(roomDTO);
                    room.setId(idRoom);
                    roomService.updateRoom(room);
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
            for (Room r : roomList){
                if (r.getId() == idRoom){
                    roomService.deleteRoom(idRoom);
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.notFound().build();
        }
    }
}
