package firstgroup.temposlack.dto;

public class UserUpdateDTO {

    private String avatar;
    private String pseudo;
    private String password;
    private String email;

    private UserPseudoPasswordDTO user;

    public String getAvatar() {
        return avatar;
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

    public UserPseudoPasswordDTO getUser() {
        return user;
    }
}
