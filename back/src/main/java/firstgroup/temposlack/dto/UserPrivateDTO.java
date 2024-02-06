package firstgroup.temposlack.dto;

public class UserPrivateDTO {

    private String pseudo;
    private String email;
    private String avatar;

    public UserPrivateDTO(String pseudo, String email, String avatar) {
        this.pseudo = pseudo;
        this.email = email;
        this.avatar = avatar;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }
}
