package firstgroup.temposlack.dto;

import firstgroup.temposlack.model.Room;

import java.util.List;

public class ServerListDTO {

    private Long id;
    private String name;
    private List<Room> roomList;

    public ServerListDTO(Long id, String name, List<Room> roomList) {
        this.id = id;
        this.name = name;
        this.roomList = roomList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Room> getRoomList() {
        return roomList;
    }
}
