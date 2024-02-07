package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dao.UserRepository;
import firstgroup.temposlack.dto.MessageDTO;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageMapper {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

    public static MessageDTO convertEntityToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUser(message.getUser().getPseudo());
        messageDTO.setContent(message.getContent());
        messageDTO.setDate(message.getDate().format(formatter));
        return messageDTO;
    }

    public static Message convertDTOtoEntity(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setDate(LocalDateTime.now());
        return message;
    }
}
