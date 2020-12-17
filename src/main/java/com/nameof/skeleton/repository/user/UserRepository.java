package com.nameof.skeleton.repository.user;

import com.nameof.skeleton.model.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

}
