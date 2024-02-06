package firstgroup.temposlack.service;

import firstgroup.temposlack.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.dao.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public void createRoom(Room room) {
        roomRepository.save(room);
    }

    public void addMessage(Long id, Message message){
        Room room = getRoomById(id).get();
        room.addMessage(message);
        roomRepository.save(room);
    }
}
