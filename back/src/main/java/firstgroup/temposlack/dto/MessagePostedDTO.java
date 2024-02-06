package firstgroup.temposlack.dto;

public class MessagePostedDTO {

    private UserPseudoPasswordDTO user;
    private String content;

    public UserPseudoPasswordDTO getUser() {
        return user;
    }

    public void setUser(UserPseudoPasswordDTO user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
