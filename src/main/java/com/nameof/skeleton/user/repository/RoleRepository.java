package com.nameof.skeleton.user.repository;

import com.nameof.skeleton.user.domain.Role;
import com.nameof.skeleton.user.domain.UserRoles;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRole(UserRoles role);

}
