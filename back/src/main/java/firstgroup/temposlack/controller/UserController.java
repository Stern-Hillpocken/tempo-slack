package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.*;
import firstgroup.temposlack.mapper.UserPrivateMapper;
import firstgroup.temposlack.mapper.UserPublicMapper;
import firstgroup.temposlack.mapper.UserSignInMapper;
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
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserPublicDTO> getPublicById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        UserPublicDTO userPublicDTO = UserPublicMapper.userToDTO(user);
        return ResponseEntity.ok(userPublicDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserPrivateDTO> getPrivateById(@RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        if (userPseudoPasswordDTO == null || !userService.isUserPseudoPasswordDTOValid(userPseudoPasswordDTO))
            return ResponseEntity.noContent().build();

        Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        UserPrivateDTO userPrivateDTO = UserPrivateMapper.userToDTO(user);
        if (user.getPseudo().equals(userPseudoPasswordDTO.getPseudo()) && user.getPassword().equals(userPseudoPasswordDTO.getPassword()))
            return ResponseEntity.ok(userPrivateDTO);
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody UserSignInDTO userSignInDTO) {
        if (userSignInDTO == null || !userService.isUserSignInDTOValid(userSignInDTO))
            return ResponseEntity.noContent().build();
        if (userSignInDTO.getPseudo() == null || userSignInDTO.getPseudo().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("pseudo");
        if (userSignInDTO.getPassword() == null || userSignInDTO.getPassword().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("password");
        if (userSignInDTO.getEmail() == null || userSignInDTO.getEmail().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("email");
        if (userSignInDTO.getAvatar() == null || userSignInDTO.getAvatar().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("avatar");

        if (userService.hasEmoji(userSignInDTO.getPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (userService.hasStrangeChar(userSignInDTO.getPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (!userService.isPasswordWellFormated(userSignInDTO.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password");
        if (!userService.isAvatarExist(userSignInDTO.getAvatar()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("avatar");

        Optional<User> userWithThisPseudo = userService.getByPseudo(userSignInDTO.getPseudo());
        if (userWithThisPseudo.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        User user = UserSignInMapper.signInDTOToUser(userSignInDTO);
        userService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/disable-account")
    public ResponseEntity<?> disableAccount(@RequestBody UserPseudoPasswordDTO userDTO) {
        if (userDTO == null || !userService.isUserPseudoPasswordDTOValid(userDTO)) return ResponseEntity.noContent().build();
        if (!userService.isUserPasswordMatching(userDTO)) return ResponseEntity.notFound().build();

        User user = userService.getByPseudo(userDTO.getPseudo()).get();
        userService.toggleAccount(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/enable-account")
    public ResponseEntity<?> enableAccount(@RequestBody UserPseudoPasswordDTO userDTO) {
        return disableAccount(userDTO);
    }

    @PutMapping("/me")
    public ResponseEntity<?> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO == null || userUpdateDTO.getOldPseudo() == null || userUpdateDTO.getNewPseudo() == null || userUpdateDTO.getOldPassword() == null || userUpdateDTO.getNewPassword() == null || userUpdateDTO.getOldEmail() == null || userUpdateDTO.getNewEmail() == null || userUpdateDTO.getOldAvatar() == null || userUpdateDTO.getNewAvatar() == null)
            return ResponseEntity.noContent().build();
        Optional<User> optionalOldUser = userService.getByPseudo(userUpdateDTO.getOldPseudo());
        if (optionalOldUser.isEmpty()) return ResponseEntity.notFound().build();

        Optional<User> optionalNewUser = userService.getByPseudo(userUpdateDTO.getNewPseudo());
        if (optionalNewUser.isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        if (userService.hasEmoji(userUpdateDTO.getNewPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (userService.hasStrangeChar(userUpdateDTO.getNewPseudo()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo");
        if (!userService.isPasswordWellFormated(userUpdateDTO.getNewPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password");
        if (!userService.isAvatarExist(userUpdateDTO.getNewAvatar()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("avatar");

        User user = optionalOldUser.get();
        if (!user.getPassword().equals(userUpdateDTO.getOldPassword())) return ResponseEntity.notFound().build();
        user.setPseudo(userUpdateDTO.getNewPseudo());
        user.setPassword(userUpdateDTO.getNewPassword());
        user.setEmail(userUpdateDTO.getNewEmail());
        user.setAvatar(userUpdateDTO.getNewAvatar());
        userService.update(user);
        return ResponseEntity.ok().build();
    }
}
