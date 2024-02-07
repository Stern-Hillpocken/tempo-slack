package firstgroup.temposlack.controller;

import firstgroup.temposlack.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import firstgroup.temposlack.dto.RoomDTO;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.service.RoomService;

import java.util.List;
import java.util.Optional;

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
        Optional<Room> optionalRoom = roomService.getRoomById(id);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Room room = optionalRoom.get();
            RoomDTO roomDTO = RoomMapper.convertToDTO(room);
            return ResponseEntity.ok(roomDTO);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        Optional<Room> optionalRoom = roomService.getRoomById(id);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Room existingRoom = optionalRoom.get();
            Room updatedRoom = RoomMapper.convertToEntity(roomDTO);
            existingRoom.setTitle(updatedRoom.getTitle());

            roomService.updateRoom(existingRoom);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        Optional<Room> optionalRoom = roomService.getRoomById(id);
        if (optionalRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            roomService.deleteRoom(id);
            return ResponseEntity.noContent().build();
        }
    }
}
