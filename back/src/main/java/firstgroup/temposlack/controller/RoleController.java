package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.RoleDTO;
import firstgroup.temposlack.mapper.RoleMapper;
import firstgroup.temposlack.model.Role;
import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.model.User;
import firstgroup.temposlack.service.RoleService;
import firstgroup.temposlack.service.ServerService;
import firstgroup.temposlack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("servers")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    ServerService serverService;
    @Autowired
    UserService userService;

    @GetMapping("roles")
    public ResponseEntity<List<Role>> findAllRole(){
        return ResponseEntity.ok(roleService.getAll());
    }

    @PostMapping("{idServer}/roles")
    public ResponseEntity<?> addRoleToServer(@PathVariable("idServer") Long idServer,@RequestBody RoleDTO roleDTO){
        Optional<Server> optionalServer = serverService.findById(idServer);
        if (optionalServer.isEmpty() || roleDTO.getName() == null || roleDTO.getName().isBlank()) return ResponseEntity.notFound().build();
        Role role = RoleMapper.convertDTOtoEntity(roleDTO);
        Server server = optionalServer.get();
        role.setServer(server);
        roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("{idServer}/{idUser}/roles")
    public ResponseEntity<?> addRoleToUser(@PathVariable("idServer") Long idServer, @PathVariable("idUser") Long idUser, @RequestBody RoleDTO roleDTO){
        Optional<Server> optionalServer = serverService.findById(idServer);
        Optional<User> optionalUser = userService.getById(idUser);
        if (optionalServer.isEmpty() || optionalUser.isEmpty() || roleDTO.getName() == null || roleDTO.getName().isBlank()) return ResponseEntity.notFound().build();
        Server server = optionalServer.get();
        User user = optionalUser.get();
        for (Role r : server.getRoleList()){
            if (r.getName().equals(roleDTO.getName())){
                r.addUser(user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.notFound().build();

    }
}
