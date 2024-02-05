package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.UserPublicDTO;
import firstgroup.temposlack.dto.UserSignInDTO;
import firstgroup.temposlack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody UserSignInDTO userSignInDTO) {
        return service.add(userSignInDTO);
    }

    @GetMapping("/{id}")
    public UserPublicDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
