package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.*;
import firstgroup.temposlack.mapper.UserPrivateMapper;
import firstgroup.temposlack.mapper.UserPublicMapper;
import firstgroup.temposlack.mapper.UserSignInMapper;
import firstgroup.temposlack.model.Room;
import firstgroup.temposlack.model.User;
import firstgroup.temposlack.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody UserSignInDTO userSignInDTO) {
        if (userSignInDTO == null || !service.isUserSignInDTOValid(userSignInDTO))
            return ResponseEntity.noContent().build();
        if (userSignInDTO.getPseudo() == null || userSignInDTO.getPseudo().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("pseudo");
        if (userSignInDTO.getPassword() == null || userSignInDTO.getPassword().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("password");
        if (userSignInDTO.getEmail() == null || userSignInDTO.getEmail().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("email");
        if (userSignInDTO.getAvatar() == null || userSignInDTO.getAvatar().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("avatar");

        if (service.hasEmoji(userSignInDTO.getPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (service.hasStrangeChar(userSignInDTO.getPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (!service.isPasswordWellFormated(userSignInDTO.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password");
        if (!service.isAvatarExist(userSignInDTO.getAvatar()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("avatar");

        Optional<User> userWithThisPseudo = service.getByPseudo(userSignInDTO.getPseudo());
        if (userWithThisPseudo.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        User user = UserSignInMapper.signInDTOToUser(userSignInDTO);
        service.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
        if (userPseudoPasswordDTO == null || !service.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();

        Optional<User> optionalUser = service.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        UserPrivateDTO userPrivateDTO = UserPrivateMapper.userToDTO(user);
        if (user.getPseudo().equals(userPseudoPasswordDTO.getPseudo()) && user.getPassword().equals(userPseudoPasswordDTO.getPassword()))
            return ResponseEntity.ok(userPrivateDTO);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/me")
    public ResponseEntity<?> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO == null || userUpdateDTO.getOldPseudo() == null || userUpdateDTO.getNewPseudo() == null || userUpdateDTO.getOldPassword() == null || userUpdateDTO.getNewPassword() == null || userUpdateDTO.getOldEmail() == null || userUpdateDTO.getNewEmail() == null || userUpdateDTO.getOldAvatar() == null || userUpdateDTO.getNewAvatar() == null)
            return ResponseEntity.noContent().build();
        Optional<User> optionalOldUser = service.getByPseudo(userUpdateDTO.getOldPseudo());
        if (optionalOldUser.isEmpty()) return ResponseEntity.notFound().build();

        Optional<User> optionalNewUser = service.getByPseudo(userUpdateDTO.getNewPseudo());
        if (optionalNewUser.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        if (service.hasEmoji(userUpdateDTO.getNewPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (service.hasStrangeChar(userUpdateDTO.getNewPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (!service.isPasswordWellFormated(userUpdateDTO.getNewPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password");
        if (!service.isAvatarExist(userUpdateDTO.getNewAvatar()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("avatar");


        User user = optionalOldUser.get();
        if (!user.getPassword().equals(userUpdateDTO.getOldPassword())) return ResponseEntity.notFound().build();
        user.setPseudo(userUpdateDTO.getNewPseudo());
        user.setPassword(userUpdateDTO.getNewPassword());
        user.setEmail(userUpdateDTO.getNewEmail());
        user.setAvatar(userUpdateDTO.getNewAvatar());
        service.update(user);
        return ResponseEntity.ok().build();
    }
}
