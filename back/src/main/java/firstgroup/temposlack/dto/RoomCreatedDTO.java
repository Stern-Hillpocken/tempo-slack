package firstgroup.temposlack.dto;

import firstgroup.temposlack.model.Room;

public class RoomCreatedDTO {

    private String title;
    private UserPseudoPasswordDTO user;

    public RoomCreatedDTO() {}

    public RoomCreatedDTO(String title, UserPseudoPasswordDTO user) {
        this.title = title;
        this.user = user;
    }

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public void setUser(UserPseudoPasswordDTO user) {
        this.user = user;
    }
}
