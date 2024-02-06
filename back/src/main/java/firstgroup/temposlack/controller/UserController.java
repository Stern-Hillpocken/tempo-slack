package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.UserPrivateDTO;
import firstgroup.temposlack.dto.UserPseudoPasswordDTO;
import firstgroup.temposlack.dto.UserPublicDTO;
import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.mapper.UserPrivateMapper;
import firstgroup.temposlack.mapper.UserPublicMapper;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.User;
import firstgroup.temposlack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody UserSignInDTO userSignInDTO) {
        if (userSignInDTO == null) return ResponseEntity.noContent().build();
        if (userSignInDTO.getPseudo() == null || userSignInDTO.getPseudo().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("pseudo");
        if (userSignInDTO.getPassword() == null || userSignInDTO.getPassword().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("password");
        if (userSignInDTO.getEmail() == null || userSignInDTO.getEmail().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("email");
        if (userSignInDTO.getAvatar() == null || userSignInDTO.getAvatar().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("avatar");
        return service.add(userSignInDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPublicDTO> getPublicById(@PathVariable Long id) {
        Optional<User> optionalUser = service.getById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        UserPublicDTO userPublicDTO = UserPublicMapper.userToDTO(user);
        return ResponseEntity.ok(userPublicDTO);
    }
    @GetMapping("/me")
    public ResponseEntity<UserPrivateDTO> getPrivateById(@RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || userPseudoPasswordDTO.getPseudo() == null || userPseudoPasswordDTO.getPseudo().isEmpty() || userPseudoPasswordDTO.getPassword() == null || userPseudoPasswordDTO.getPassword().isEmpty()) return ResponseEntity.noContent().build();

        Optional<User> optionalUser = service.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        UserPrivateDTO userPrivateDTO = UserPrivateMapper.userToDTO(user);
        if (user.getPseudo().equals(userPseudoPasswordDTO.getPseudo()) && user.getPassword().equals(userPseudoPasswordDTO.getPassword())) return ResponseEntity.ok(userPrivateDTO);
        return ResponseEntity.notFound().build();
    }
}
