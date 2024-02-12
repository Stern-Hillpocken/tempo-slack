package firstgroup.temposlack.model;

import firstgroup.temposlack.enums.ReactionType;
import firstgroup.temposlack.mapper.UserIdSetConverter;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private LocalDateTime date;
    private String content;

    @ElementCollection
    @Convert(converter = UserIdSetConverter.class)
    @CollectionTable(name = "message_reactions", joinColumns = @JoinColumn(name = "message_id"))
    @MapKeyColumn(name = "reaction_type")
    @Column(name = "user_ids")
    private Map<String, Set<Long>> reactions = new HashMap<>();

    public Message() {
    }

    public Message(User user, LocalDateTime date, String content) {
        this.user = user;
        this.date = date;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Set<Long>> getReactions() {
        return reactions;
    }

    public void setReactions(Map<String, Set<Long>> reactions) {
        this.reactions = reactions;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", reactions=" + reactions +
                '}';
    }
}
