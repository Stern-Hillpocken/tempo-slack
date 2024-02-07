package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.RoomCreatedDTO;
import firstgroup.temposlack.model.Room;

public class RoomDeletedMapper {

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
