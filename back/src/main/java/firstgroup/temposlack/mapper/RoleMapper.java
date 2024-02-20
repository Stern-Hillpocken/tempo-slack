package firstgroup.temposlack.mapper;

import firstgroup.temposlack.dto.RoleDTO;
import firstgroup.temposlack.model.Role;

public class RoleMapper {
    public static Role convertDTOtoEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return role;
    }
}
