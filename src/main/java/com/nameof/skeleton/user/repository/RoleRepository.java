package com.nameof.skeleton.user.repository;

import com.nameof.skeleton.user.domain.Role;
import com.nameof.skeleton.user.model.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(UserRoles role);

}
