package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.UserRepository;
import firstgroup.temposlack.dto.UserPublicDTO;
import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.mapper.UserPublicMapper;
import firstgroup.temposlack.mapper.UserSignInMapper;
import firstgroup.temposlack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    UserRepository repository;
    
    public UserPublicDTO getById(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new RuntimeException("User id: "+id+" not found.")
        );
        return UserPublicMapper.userToDTO(user);
    }

    public User getByPseudo(String pseudo) {
        User user = repository.findByPseudo(pseudo).orElseThrow(
                () -> new RuntimeException("Pseudo "+pseudo+" not found.")
        );
        return user;
    }

    public ResponseEntity<?> add(UserSignInDTO userSignInDTO) {
        User user = UserSignInMapper.signInDTOToUser(userSignInDTO);
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
