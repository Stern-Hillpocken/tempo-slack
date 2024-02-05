package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.RoomDTO;
import firstgroup.temposlack.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomMapper {

    public static RoomDTO convertToDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setTitle(room.getTitle());
        return roomDTO;
    }

    public static List<RoomDTO> convertToDTOs(List<Room> rooms) {
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(convertToDTO(room));
        }
        return roomDTOs;
    }

    public static Room convertToEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setTitle(roomDTO.getTitle());
        return room;
    }
}
