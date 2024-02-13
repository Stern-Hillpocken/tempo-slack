package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.ServerRepository;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.model.Server;
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
    @Autowired
    ServerRepository serverRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public void createRoom(Room room, Server server) {
        server.addRoom(room);
        roomRepository.save(room);
        serverRepository.save(server);
    }

    public void addMessage(Room room, Message message) {
        room.addMessage(message);
        roomRepository.save(room);
    }

    public void updateRoom(Room updatedRoom) {
        roomRepository.save(updatedRoom);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
