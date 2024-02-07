package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.RoleRepository;
import firstgroup.temposlack.dao.ServerRepository;
import firstgroup.temposlack.model.Role;
import firstgroup.temposlack.model.Server;
import firstgroup.temposlack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ServerRepository serverRepository;

    public void createRole(Role role, Server server){
        server.addRole(role);
        roleRepository.save(role);
        serverRepository.save(server);
    }

    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Long id){
        return roleRepository.findById(id);
    }

    public void delete(Long id, Server server){
        server.deleteRole(id);
        roleRepository.deleteById(id);
    }

    public void deleteRoleUser(Role role, User user){
        role.deleteUser(user);
        roleRepository.save(role);
    }

    public void addRoleUser(Role role, User user){
        role.addUser(user);
        roleRepository.save(role);
    }

    public void updateName(Role role, String name){
        role.setName(name);
        roleRepository.save(role);
    }

    public boolean isOwner(User user, Server server){
        for (Role r : server.getRoleList()){
            if (r.getName().equals("Owner")){
                for (User u : r.getUserList()){
                    if (u.getPseudo().equals(user.getPseudo())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
