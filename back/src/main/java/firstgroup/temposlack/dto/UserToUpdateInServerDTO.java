package firstgroup.temposlack.dto;

public class UserToUpdateInServerDTO {

    private UserPseudoPasswordDTO user;
    private String userPseudoToUpdate;

    public UserToUpdateInServerDTO(UserPseudoPasswordDTO user, String userPseudoToUpdate) {
        this.user = user;
        this.userPseudoToUpdate = userPseudoToUpdate;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public String getUserPseudoToUpdate() {
        return userPseudoToUpdate;
    }
}
