package firstgroup.temposlack.model;

import firstgroup.temposlack.model.Room;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name="room_id")
    private List<Room> roomList = new ArrayList<>();
    @ManyToMany
    private List<User> userList = new ArrayList<>();
//    @ManyToMany
//    @JoinTable()
//    private List<Role> roleList;

    public Server() {
    }

    public Server(String name, User user) {
        this.name = name;
        this.userList.add(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void addRoom(Room room){
        roomList.add(room);
    }


//    public List<Role> getRoleList() {
//        return roleList;
//    }
//
//    public void setRoleList(List<Role> roleList) {
//        this.roleList = roleList;
//    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roomList=" + roomList +
                ", userList=" + userList +
               // ", roleList=" + roleList +
                '}';
    }
}
