package firstgroup.temposlack.dto;

public class RoleDTO {

    private String name;
    private UserPseudoPasswordDTO user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public void setUser(UserPseudoPasswordDTO user) {
        this.user = user;
    }
}
