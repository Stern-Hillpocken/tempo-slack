package firstgroup.temposlack.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean isRemovable = true;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList<>();

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

    public boolean isRemovable() {
        return isRemovable;
    }

    public void setRemovable(boolean removable) {
        isRemovable = removable;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public void deleteMessage(Long id) {
        for (Message message : messageList) {
            if (message.getId().equals(id)) {
                messageList.remove(message);
                break;
            }
        }
    }


}
