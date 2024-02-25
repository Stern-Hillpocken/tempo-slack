package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.UserRepository;
import firstgroup.temposlack.dto.UserPseudoPasswordDTO;
import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.enums.AvatarEnum;
import firstgroup.temposlack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByPseudo(String pseudo) {
        return userRepository.findByPseudo(pseudo);
    }

    public boolean isUserPasswordMatching(UserPseudoPasswordDTO user) {
        Optional<User> optionalUser = getByPseudo(user.getPseudo());
        if (optionalUser.isEmpty()) return false;
        if (!optionalUser.get().getPassword().equals(user.getPassword())) return false;
        return true;
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public boolean isUserPseudoPasswordDTOValid(UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO.getPseudo() == null || userPseudoPasswordDTO.getPseudo().isBlank() || userPseudoPasswordDTO.getPassword() == null || userPseudoPasswordDTO.getPassword().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean isUserSignInDTOValid(UserSignInDTO userSignInDTO) {
        if (userSignInDTO.getPseudo() == null || userSignInDTO.getPassword() == null || userSignInDTO.getEmail() == null || userSignInDTO.getAvatar() == null)
            return false;
        return true;
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public boolean hasEmoji(String str) {
        // So : other symbol
        Matcher m = Pattern.compile("\\p{So}").matcher(str);
        return m.find();
    }

    public boolean isAvatarExist(String avatar) {
        for (AvatarEnum ae : AvatarEnum.values()) {
            if (ae.name().equalsIgnoreCase(avatar)) return true;
        }
        return false;
    }

    public boolean hasStrangeChar(String str) {
        // W = !w = no-word character
        Pattern pattern = Pattern.compile("\\W");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public boolean isPseudoWellFormatted(String password) {
        if (password.length() < 3) return false;
        if (password.length() > 15) return false;
        return true;
    }

    public boolean isPasswordWellFormatted(String password) {
        if (password.length() < 6) return false;
        if (password.length() > 20) return false;
        if (!Pattern.compile("\\W").matcher(password).find()) return false;
        //if (!Pattern.compile("\\d").matcher(password).find()) return false;
        return true;
    }

    public void toggleAccount(User user) {
        user.setAccountIsActive(!user.getAccountIsActive());
        userRepository.save(user);
    }
}
