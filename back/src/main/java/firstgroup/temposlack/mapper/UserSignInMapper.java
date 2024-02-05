package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.model.User;

public class UserSignInMapper {

    public static User signInDTOToUser(UserSignInDTO userSignInDTO) {
        return new User(userSignInDTO.getPseudo(), userSignInDTO.getPassword(), userSignInDTO.getEmail(), userSignInDTO.getAvatar());
    }
}
