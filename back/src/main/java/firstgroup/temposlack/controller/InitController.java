package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("init")
public class InitController {

    @Autowired
    UserController userController;

    @Autowired
    ServerController serverController;

    @GetMapping
    public void init(){
        userController.add(new UserSignInDTO("Riri", "riri!!", "riri@email.fr", "base"));
        userController.add(new UserSignInDTO("Fifi", "fifi!!", "fifi@email.fr", "first"));
        userController.add(new UserSignInDTO("Loulou", "loulou!!", "loulou@email.fr", "premium"));
        userController.add(new UserSignInDTO("John", "john!!", "john@email.fr", "base"));

        UserPseudoPasswordDTO riri = new UserPseudoPasswordDTO("Riri", "riri!!");
        UserPseudoPasswordDTO fifi = new UserPseudoPasswordDTO("Fifi", "fifi!!");
        UserPseudoPasswordDTO loulou = new UserPseudoPasswordDTO("Loulou", "loulou!!");
        UserPseudoPasswordDTO john = new UserPseudoPasswordDTO("John", "john!!");

        serverController.addServer(new ServerCreatedDTO("My First Serv", riri));
        serverController.addUserToServer(1L, new UserAddedToServerDTO(riri, "Fifi"));
        serverController.addUserToServer(1L, new UserAddedToServerDTO(riri, "Loulou"));
        serverController.addRoom(1L, new RoomCreatedDTO("Nouvelle salle", riri));
        serverController.addMessage(1L, 2L, new MessagePostedDTO(riri, "Salut tout le monde !"));
        serverController.addMessage(1L, 2L, new MessagePostedDTO(fifi, "Salut Riri :)"));
        serverController.addMessage(1L, 2L, new MessagePostedDTO(riri, "Comment ça va ?"));

        serverController.addServer(new ServerCreatedDTO("The Long John's Server", john));
    }

}
