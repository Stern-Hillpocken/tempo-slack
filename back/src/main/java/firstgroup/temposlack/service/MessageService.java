package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.MessageRepository;
import firstgroup.temposlack.dao.RoomRepository;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.model.Room;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RoomRepository roomRepository;

    public void add(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Long id) {
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

    public void addReaction(Long messageId, String reactionType, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));
        Set<Long> userIds = message.getReactions().getOrDefault(reactionType, new HashSet<>());
        userIds.add(userId);
        message.getReactions().put(reactionType, userIds);
        messageRepository.save(message);
    }
    public void removeReaction(Long messageId, String reactionType, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));
        if (message.getReactions().containsKey(reactionType)) {
            Set<Long> userIds = message.getReactions().get(reactionType);
            if (userIds.remove(userId)) { // Vérifie si l'utilisateur avait réagi et supprime sa réaction
                if (userIds.isEmpty()) {
                    message.getReactions().remove(reactionType); // Supprime le type de réaction s'il n'y a plus d'utilisateurs
                }
                messageRepository.save(message); // Sauvegarde le message avec les réactions mises à jour
            }
        }
    }

}
