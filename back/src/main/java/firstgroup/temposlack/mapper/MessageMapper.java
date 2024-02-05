package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.MessageDTO;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageMapper {
    @Autowired
    static UserService userService;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

    public static MessageDTO convertEntityToDTO(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUser(message.getUser().getPseudo());
        messageDTO.setContent(message.getContent());
        messageDTO.setDate(message.getDate().format(formatter));
        //messageDTO.setAvatar(message.getUser().getAvatar());
        return messageDTO;
    }

    public static Message convertDTOtoEntity(MessageDTO messageDTO){
        Message message = new Message();
        message.setUser(userService.getByPseudo(messageDTO.getUser()));
        message.setContent(messageDTO.getContent());
        message.setDate(LocalDateTime.parse(messageDTO.getDate(),formatter));
        return message;
    }
}
