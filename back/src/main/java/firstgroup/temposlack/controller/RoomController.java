package firstgroup.temposlack.controller;

import firstgroup.temposlack.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import firstgroup.temposlack.dto.RoomDTO;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
     RoomService roomService;

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return RoomMapper.convertToDTOs(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        RoomDTO roomDTO = RoomMapper.convertToDTO(room);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping
    public ResponseEntity<Void> createRoom(@RequestBody RoomDTO roomDTO) {
        Room room = RoomMapper.convertToEntity(roomDTO);
        roomService.createRoom(room);
        return ResponseEntity.created(null).build();
    }


}
