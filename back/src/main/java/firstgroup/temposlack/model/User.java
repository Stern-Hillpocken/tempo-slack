package firstgroup.temposlack.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import firstgroup.temposlack.model.Server;

@Entity(name = "users") // because 'user' is reserved in postgresql
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String password;
    private String email;
    private String avatar;
    private LocalDateTime lastUpdate;

    @ManyToMany
    private List<Server> serverList;

    //private List<Role> roleList;

    public User() {
    }

    public User(String pseudo, String password, String email, String avatar) {
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.lastUpdate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
