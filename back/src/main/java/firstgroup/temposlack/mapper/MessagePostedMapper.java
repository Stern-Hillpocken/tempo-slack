package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.MessageDTO;
import firstgroup.temposlack.dto.MessagePostedDTO;
import firstgroup.temposlack.model.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessagePostedMapper {

    public static Message convertDTOtoEntity(MessagePostedDTO messagePostedDTO){
        Message message = new Message();
        message.setContent(messagePostedDTO.getContent());
        message.setDate(LocalDateTime.now());
        return message;
    }
}
