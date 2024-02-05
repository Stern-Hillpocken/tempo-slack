package firstgroup.temposlack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.dao.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void createRoom(Room room) {
        roomRepository.save(room);
    }


}
