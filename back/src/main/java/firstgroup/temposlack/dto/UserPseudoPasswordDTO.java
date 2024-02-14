package firstgroup.temposlack.dto;

public class UserPseudoPasswordDTO {

    private String pseudo;
    private String password;

    public UserPseudoPasswordDTO(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }
}
