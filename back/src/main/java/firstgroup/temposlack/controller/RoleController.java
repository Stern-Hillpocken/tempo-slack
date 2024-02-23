package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.RoleDTO;
import firstgroup.temposlack.dto.UserPseudoPasswordDTO;
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
    public ResponseEntity<List<Role>> findAllRole() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("{idServer}/roles")
    public ResponseEntity<List<Role>> findAllRoleInServer(@PathVariable("idServer") Long idServer) {
        Optional<Server> optionalServer = serverService.getById(idServer);
        if (optionalServer.isEmpty()) return ResponseEntity.notFound().build();
        Server server = optionalServer.get();
        return ResponseEntity.ok(server.getRoleList());
    }

    @PostMapping("{idServer}/roles")
    public ResponseEntity<?> addRoleToServer(@PathVariable("idServer") Long idServer, @RequestBody RoleDTO roleDTO) {
        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<User> optionalUser = userService.getByPseudo(roleDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty() || roleDTO.getName() == null || roleDTO.getName().isBlank())
            return ResponseEntity.notFound().build();
        Server server = optionalServer.get();
        if (!userService.isUserPasswordMatching(roleDTO.getUser())) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        if (!server.isUserInServer(user)) {
            return ResponseEntity.badRequest().build();
        }
        if (!roleService.isOwner(user, server)) return ResponseEntity.badRequest().build();
        for (Role role : server.getRoleList()){
            if(role.getName().equals(roleDTO.getName())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Role role = RoleMapper.convertDTOtoEntity(roleDTO);
        role.setServer(server);
        roleService.createRole(role, server);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("{idServer}/{idUser}/roles")
    public ResponseEntity<?> addRoleToUser(@PathVariable("idServer") Long idServer, @PathVariable("idUser") Long idUser, @RequestBody RoleDTO roleDTO) {
        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<User> optionalUser = userService.getById(idUser);
        Optional<User> optionalUserOwner = userService.getByPseudo(roleDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty() || optionalUserOwner.isEmpty() || roleDTO.getName() == null || roleDTO.getName().isBlank())
            return ResponseEntity.notFound().build();
        Server server = optionalServer.get();
        User user = optionalUser.get();
        if (!userService.isUserPasswordMatching(roleDTO.getUser())) {
            return ResponseEntity.notFound().build();
        }
        User userOwner = optionalUserOwner.get();
        if (!server.isUserInServer(user) || !server.isUserInServer(userOwner)) {
            return ResponseEntity.badRequest().build();
        }
        if (!roleService.isOwner(userOwner, server)) return ResponseEntity.badRequest().build();
        for (Role r : server.getRoleList()) {
            if (r.getName().equals(roleDTO.getName())) {
                roleService.addRoleUser(r, user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("roles/{idRole}")
    public ResponseEntity<?> deleteRoleServer(@PathVariable("idRole") Long idRole, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        Optional<Role> optionalRole = roleService.getById(idRole);
        Optional<User> optionalUser = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalRole.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();
        if (!userService.isUserPasswordMatching(userPseudoPasswordDTO)) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        Role role = optionalRole.get();
        Server server = role.getServer();
        if (!server.isUserInServer(user) || !roleService.isOwner(user, server) || role.getName().equals("owner")) {
            return ResponseEntity.badRequest().build();
        }
        roleService.delete(idRole, server);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{idUser}/roles/{idRole}")
    public ResponseEntity<?> deleteRoleUser(@PathVariable("idRole") Long idRole, @PathVariable("idUser") Long idUser, @RequestBody UserPseudoPasswordDTO userPseudoPasswordDTO) {
        Optional<Role> optionalRole = roleService.getById(idRole);
        Optional<User> optionalUser = userService.getById(idUser);
        Optional<User> optionalUserOwner = userService.getByPseudo(userPseudoPasswordDTO.getPseudo());
        if (optionalRole.isEmpty() || optionalUser.isEmpty() || optionalUserOwner.isEmpty())
            return ResponseEntity.notFound().build();
        User user = optionalUser.get();
        if (!userService.isUserPasswordMatching(userPseudoPasswordDTO)) {
            return ResponseEntity.notFound().build();
        }
        User userOwner = optionalUserOwner.get();
        Role role = optionalRole.get();
        Server server = role.getServer();
        if (!server.isUserInServer(user) || !server.isUserInServer(userOwner) || !roleService.isOwner(userOwner, server)) {
            return ResponseEntity.badRequest().build();
        }
        roleService.deleteRoleUser(role, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("roles/{idRole}")
    public ResponseEntity<?> editRoleName(@PathVariable("idRole") Long idRole, @RequestBody RoleDTO roleDTO) {
        Optional<Role> optionalRole = roleService.getById(idRole);
        Optional<User> optionalUser = userService.getByPseudo(roleDTO.getUser().getPseudo());
        if (optionalRole.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();
        if (!userService.isUserPasswordMatching(roleDTO.getUser())) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        Role role = optionalRole.get();
        Server server = role.getServer();
        if (!server.isUserInServer(user) || !roleService.isOwner(user, server)) {
            return ResponseEntity.badRequest().build();
        }
        for (Role r : server.getRoleList()){
            if(r.getName().equals(roleDTO.getName())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        roleService.updateName(role, roleDTO.getName());
        return ResponseEntity.ok().build();
    }
}
