package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.MessageRepository;
import firstgroup.temposlack.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void add(Message message){
        messageRepository.save(message);
    }

    public List<Message> getAll(){
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Long id){
        return messageRepository.findById(id);
    }

    public void delete(Long id){
        messageRepository.deleteById(id);
    }

    public void update(Message message){
        messageRepository.save(message);
    }
}
