package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.UserPrivateDTO;
import firstgroup.temposlack.model.User;

public class UserPrivateMapper {

    public static UserPrivateDTO userToDTO(User user) {
        return new UserPrivateDTO(user.getPseudo(), user.getEmail(), user.getAvatar());
    }
}
