package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.RoleRepository;
import firstgroup.temposlack.dao.ServerRepository;
import firstgroup.temposlack.model.Role;
import firstgroup.temposlack.model.Server;
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
}
