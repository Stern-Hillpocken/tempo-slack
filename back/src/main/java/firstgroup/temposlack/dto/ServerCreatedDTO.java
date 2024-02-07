package firstgroup.temposlack.dto;

import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.User;

import java.util.List;

public class ServerCreatedDTO {

    private String name;
    private List<Room> roomList;
    private List<User> userList;
    private User user;

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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
