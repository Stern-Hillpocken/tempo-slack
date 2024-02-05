package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.MessageDTO;
import firstgroup.temposlack.model.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageMapper {

    public static MessageDTO convertEntityToDTO(Message message){
        MessageDTO messageDTO = new MessageDTO();
        //messageDTO.setUser(message.getUser().getPseudo());
        messageDTO.setContent(message.getContent());
        messageDTO.setDate(message.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        //messageDTO.setAvatar(message.getUser().getAvatar());
        return messageDTO;
    }

    public static Message convertDTOtoEntity(MessageDTO messageDTO){
        Message message = new Message();
        //message.setUser();
        message.setContent(messageDTO.getContent());
        //message.setDate();
        return message;
    }
}
