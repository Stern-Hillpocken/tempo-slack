package firstgroup.temposlack;

import firstgroup.temposlack.controller.UserController;
import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class TempoSlackUserTests {

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @Test
    void userPseudoExist() {
        UserSignInDTO user = new UserSignInDTO("Mwa", "monMot2P@sse", "e@mail.com", "base");
        userController.add(user);
        UserSignInDTO nextUser = new UserSignInDTO("Mwa", "minMot2P@sse", "i@mail.com", "base");
        Assertions.assertEquals(
                userController.add(user),
                ResponseEntity.status(HttpStatus.CONFLICT).build()
        );
    }

    @Test
    void creationWithEmoji() {
        UserSignInDTO user = new UserSignInDTO("Mwa\uD83D\uDD25haha", "monMotDePasse", "email", "base");
        Assertions.assertEquals(
                userController.add(user),
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pseudo")
        );
    }

    @Test
    void correctPassword() {
        Assertions.assertTrue(userService.isPasswordWellFormated("jK4U@p"));
    }

    @Test
    void wrongPassword() {
        Assertions.assertFalse(userService.isPasswordWellFormated("jlopmm"));
    }
}
