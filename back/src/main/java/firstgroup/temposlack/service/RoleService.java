package firstgroup.temposlack.service;

import firstgroup.temposlack.dao.RoleRepository;
import firstgroup.temposlack.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void createRole(Role role){
        roleRepository.save(role);
    }

    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Long id){
        return roleRepository.findById(id);
    }

    public void delete(Long id){
        roleRepository.deleteById(id);
    }
}
