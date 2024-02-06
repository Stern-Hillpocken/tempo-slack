package firstgroup.temposlack.dto;

import firstgroup.temposlack.model.Room;

import java.util.List;

public class ServerDTO {

    private String name;
    private List<Room> roomList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
