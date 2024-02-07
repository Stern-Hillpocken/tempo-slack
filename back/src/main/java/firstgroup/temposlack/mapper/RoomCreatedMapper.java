package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.MessagePostedDTO;
import firstgroup.temposlack.dto.RoomCreatedDTO;
import firstgroup.temposlack.dto.RoomDTO;
import firstgroup.temposlack.dto.UserPseudoPasswordDTO;
import firstgroup.temposlack.model.Message;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomCreatedMapper {

    public static Room convertDTOToEntity(RoomCreatedDTO roomCreatedDTO) {
        Room room = new Room();
        room.setTitle(roomCreatedDTO.getTitle());
        return room;
    }
    public static RoomCreatedDTO convertToDTO (Room room) {
        RoomCreatedDTO roomCreatedDTO = new RoomCreatedDTO();
        roomCreatedDTO.setId(room.getId());
        roomCreatedDTO.setTitle(room.getTitle());
        return roomCreatedDTO;
    }

}
