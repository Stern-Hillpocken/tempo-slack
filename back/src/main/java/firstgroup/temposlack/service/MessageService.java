package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.MessageRepository;
import firstgroup.temposlack.dao.RoomRepository;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    RoomRepository roomRepository;

    public void add(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> getById(Long id) {
        return messageRepository.findById(id);
    }

    public void delete(Long id, Room room) {
        room.deleteMessage(id);
        roomRepository.save(room);
        messageRepository.deleteById(id);
    }

    public void update(Message message) {
        message.setDate(LocalDateTime.now());
        messageRepository.save(message);
    }
}
