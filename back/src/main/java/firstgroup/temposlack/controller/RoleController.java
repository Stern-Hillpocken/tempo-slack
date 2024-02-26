package firstgroup.temposlack.controller;

import firstgroup.temposlack.dto.RoleAddUserDTO;
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

    @PostMapping("{idServer}/roles/add-user")
    public ResponseEntity<?> addRoleToUser(@PathVariable("idServer") Long idServer, @RequestBody RoleAddUserDTO roleAddUserDTO) {
        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<User> optionalUserToAdd = userService.getByPseudo(roleAddUserDTO.getUserToAdd());
        Optional<User> optionalUserOwner = userService.getByPseudo(roleAddUserDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUserToAdd.isEmpty() || optionalUserOwner.isEmpty() || roleAddUserDTO.getUserToAdd() == null || roleAddUserDTO.getUserToAdd().isBlank())
            return ResponseEntity.notFound().build();
        Server server = optionalServer.get();
        User userToAdd = optionalUserToAdd.get();
        if (!userService.isUserPasswordMatching(roleAddUserDTO.getUser())) {
            return ResponseEntity.notFound().build();
        }
        User userOwner = optionalUserOwner.get();
        if (!server.isUserInServer(userToAdd) || !server.isUserInServer(userOwner)) {
            return ResponseEntity.badRequest().build();
        }
        if (!roleService.isOwner(userOwner, server)) return ResponseEntity.badRequest().build();
        for (Role r : server.getRoleList()) {
            if (r.getName().equals(roleAddUserDTO.getRoleName())) {
                roleService.addRoleUser(r, userToAdd);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("{idServer}/roles/delete")
    public ResponseEntity<?> deleteRoleServer(@PathVariable("idServer") Long idServer, @RequestBody RoleDTO roleDTO) {
        Optional<Server> optionalServer = serverService.getById(idServer);
        Optional<User> optionalUser = userService.getByPseudo(roleDTO.getUser().getPseudo());
        if (optionalServer.isEmpty() || optionalUser.isEmpty()) return ResponseEntity.notFound().build();
        if (!userService.isUserPasswordMatching(roleDTO.getUser())) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        Server server = optionalServer.get();
        Role role = RoleMapper.convertDTOtoEntity(roleDTO);
        if (!server.isUserInServer(user) || !roleService.isOwner(user, server) || role.getName().equals("owner")) {
            return ResponseEntity.badRequest().build();
        }
        for (Role r : server.getRoleList() ){
            if(r.getName().equals(role.getName())){
                role.setId(r.getId());
                break;
            }
        }
        if (role.getId()!=null){
            roleService.delete(role.getId(), server);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
