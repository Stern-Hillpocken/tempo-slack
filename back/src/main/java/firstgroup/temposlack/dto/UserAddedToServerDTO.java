package firstgroup.temposlack.dto;

public class UserAddedToServerDTO {

    private UserPseudoPasswordDTO user;
    private String userPseudoToAdd;

    public UserAddedToServerDTO(UserPseudoPasswordDTO user, String userPseudoToAdd) {
        this.user = user;
        this.userPseudoToAdd = userPseudoToAdd;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public String getUserPseudoToAdd() {
        return userPseudoToAdd;
    }
}
