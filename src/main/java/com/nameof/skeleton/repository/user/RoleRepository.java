package com.nameof.skeleton.repository.user;

import com.nameof.skeleton.model.user.Role;
import com.nameof.skeleton.model.user.UserRoles;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(UserRoles role);

}
