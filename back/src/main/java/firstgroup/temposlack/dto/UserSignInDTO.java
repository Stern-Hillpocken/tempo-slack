package firstgroup.temposlack.dto;

public class UserSignInDTO {

    private String pseudo;
    private String password;
    private String email;
    private String avatar;

    public UserSignInDTO(String pseudo, String password, String email, String avatar) {
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
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
}
