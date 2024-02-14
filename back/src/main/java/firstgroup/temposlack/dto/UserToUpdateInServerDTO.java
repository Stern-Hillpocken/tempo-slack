package firstgroup.temposlack.dto;

public class UserToUpdateInServerDTO {

    private UserPseudoPasswordDTO user;
    private String userPseudoToUpdate;

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public String getUserPseudoToUpdate() {
        return userPseudoToUpdate;
    }
}
