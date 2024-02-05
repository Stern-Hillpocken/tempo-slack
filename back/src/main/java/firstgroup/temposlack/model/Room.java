package firstgroup.temposlack.model;

import jakarta.persistence.*;

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private list <Role> permissions;
    private list <Message> MessageList;

    public Room() {
    }

    public Room(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public list <Role> getPermissions() {
        return permissions;
    }

    public void setPermissions(list <Role> permissions) {
        this.permissions = permissions;

        public list <Message> getMessageList(lidt <Message> MessageList) {
            this.MessageList = MessageList;
        }

    }
}
