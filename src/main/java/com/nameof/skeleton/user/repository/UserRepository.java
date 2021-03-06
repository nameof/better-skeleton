package com.nameof.skeleton.user.repository;

import com.nameof.skeleton.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Page<User> findByFirstName(String firstName, PageRequest request);
}
