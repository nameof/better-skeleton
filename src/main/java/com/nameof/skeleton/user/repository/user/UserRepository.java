package com.nameof.skeleton.user.repository.user;

import com.nameof.skeleton.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
