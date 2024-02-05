package firstgroup.temposlack.dto;

public class UserPublicDTO {

    private String pseudo;
    private String avatar;

    public UserPublicDTO(String pseudo, String avatar) {
        this.pseudo = pseudo;
        this.avatar = avatar;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getAvatar() {
        return avatar;
    }
}
