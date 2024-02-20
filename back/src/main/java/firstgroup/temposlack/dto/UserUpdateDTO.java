package firstgroup.temposlack.dto;

public class UserUpdateDTO {

    private String oldPseudo;
    private String oldPassword;
    private String oldEmail;
    private String oldAvatar;

    private String newPseudo;
    private String newPassword;
    private String newEmail;
    private String newAvatar;

    public String getOldPseudo() {
        return oldPseudo;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public String getOldAvatar() {
        return oldAvatar;
    }

    public String getNewPseudo() {
        return newPseudo;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getNewAvatar() {
        return newAvatar;
    }
}
