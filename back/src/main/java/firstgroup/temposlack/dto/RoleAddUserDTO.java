package firstgroup.temposlack.dto;

public class RoleAddUserDTO {

    private String roleName;
    private UserPseudoPasswordDTO user;
    private String userToAdd;

    public String getRoleName() {
        return roleName;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public String getUserToAdd() {
        return userToAdd;
    }
}
