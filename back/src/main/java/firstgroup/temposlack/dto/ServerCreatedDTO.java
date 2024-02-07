package firstgroup.temposlack.dto;

import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.User;

import java.util.List;

public class ServerCreatedDTO {

    private String name;
    private UserPseudoPasswordDTO user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public void setUser(UserPseudoPasswordDTO user) {
        this.user = user;
    }
}
