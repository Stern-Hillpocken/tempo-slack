package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.UserRepository;
import firstgroup.temposlack.dto.UserPrivateDTO;
import firstgroup.temposlack.dto.UserPseudoPasswordDTO;
import firstgroup.temposlack.dto.UserPublicDTO;
import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.enums.AvatarEnum;
import firstgroup.temposlack.mapper.UserPublicMapper;
import firstgroup.temposlack.mapper.UserSignInMapper;
import firstgroup.temposlack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<User> getByPseudo(String pseudo) {
        return repository.findByPseudo(pseudo);
    }

    public boolean isUserMatching(UserPseudoPasswordDTO user) {
        Optional<User> optionalUser = getByPseudo(user.getPseudo());
        if (optionalUser.isEmpty()) return false;
        if (!optionalUser.get().getPassword().equals(user.getPassword())) return false;
        return true;
    }

    public void add(User user) {
        repository.save(user);
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
        repository.save(user);
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

    public boolean isPasswordWellFormated(String password) {
        if (password.length() < 6) return false;
        if (!Pattern.compile("\\W").matcher(password).find()) return false;
        return true;
    }
}
