package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.UserRepository;
import firstgroup.temposlack.dto.UserPrivateDTO;
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
        Optional<User> user = repository.findById(id);
        return user;
    }

    public Optional<User> getByPseudo(String pseudo) {
        Optional<User> user = repository.findByPseudo(pseudo);
        return user;
    }

    public ResponseEntity<?> add(UserSignInDTO userSignInDTO) {
        User user = UserSignInMapper.signInDTOToUser(userSignInDTO);
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
