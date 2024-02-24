package firstgroup.temposlack.dto;

public class UserUpdatePasswordDTO {

    private String password;
    private UserPseudoPasswordDTO user;

    public String getPassword() {
        return password;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }
}
