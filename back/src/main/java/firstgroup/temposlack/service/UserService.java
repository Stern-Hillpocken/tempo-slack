package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.UserRepository;
import firstgroup.temposlack.dto.UserPrivateDTO;
import firstgroup.temposlack.dto.UserPseudoPasswordDTO;
import firstgroup.temposlack.dto.UserPublicDTO;
import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.mapper.UserPublicMapper;
import firstgroup.temposlack.mapper.UserSignInMapper;
import firstgroup.temposlack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void add(User user) {
        repository.save(user);
    }

    public boolean isUserPseudoPasswordValid(UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO.getPseudo() == null || userPseudoPasswordDTO.getPseudo().isBlank() || userPseudoPasswordDTO.getPassword() == null || userPseudoPasswordDTO.getPassword().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean isUserSignInDTOValid(UserSignInDTO userSignInDTO) {
        if (userSignInDTO.getPseudo() == null || userSignInDTO.getPassword() == null || userSignInDTO.getEmail() == null || userSignInDTO.getAvatar() == null) return false;
        return true;
    }

    public void update(User user) {
        repository.save(user);
    }
}
