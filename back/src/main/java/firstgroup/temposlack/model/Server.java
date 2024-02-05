package firstgroup.temposlack.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name="room_id")
    private List<Room> roomList;
    @OneToMany
    @JoinColumn(name="user_id")
    private List<User> userList;
    @ManyToMany
    @JoinTable()
    private List<Role> roleList;

    public Server() {
    }

    public Server(String name) {
        this.name = name;
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


    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roomList=" + roomList +
                ", userList=" + userList +
                ", roleList=" + roleList +
                '}';
    }
}
