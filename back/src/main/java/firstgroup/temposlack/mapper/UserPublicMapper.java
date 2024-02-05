package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.UserPublicDTO;
import firstgroup.temposlack.model.User;

public class UserPublicMapper {
    public static UserPublicDTO userToDTO(User user) {
        return new UserPublicDTO(user.getPseudo(), user.getAvatar());
    }
}
