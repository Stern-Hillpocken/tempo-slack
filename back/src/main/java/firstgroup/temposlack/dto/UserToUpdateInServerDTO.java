package firstgroup.temposlack.dto;

public class UserAddedToServerDTO {

    private UserPseudoPasswordDTO user;
    private String userPseudoToAdd;

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public String getUserPseudoToAdd() {
        return userPseudoToAdd;
    }
}
