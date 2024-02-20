package firstgroup.temposlack.dto;

public class UserToUpdateInServerDTO {

    private UserPseudoPasswordDTO user;
    private String userPseudoToUpdate;

    public UserAddedToServerDTO(UserPseudoPasswordDTO user, String userPseudoToAdd) {
        this.user = user;
        this.userPseudoToAdd = userPseudoToAdd;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public String getUserPseudoToUpdate() {
        return userPseudoToUpdate;
    }
}
