package firstgroup.temposlack.dto;

public class UserUpdateAvatarDTO {

    private String avatar;
    private UserPseudoPasswordDTO user;

    public String getAvatar() {
        return avatar;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }
}
