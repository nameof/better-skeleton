package com.nameof.skeleton;

import com.nameof.skeleton.model.user.Role;
import com.nameof.skeleton.model.user.User;
import com.nameof.skeleton.model.user.UserRoles;
import com.nameof.skeleton.repository.user.RoleRepository;
import com.nameof.skeleton.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkeletonApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            //Create Admin and Passenger Roles
            Role adminRole = roleRepository.findByRole(UserRoles.ADMIN);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole(UserRoles.ADMIN);
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole(UserRoles.PASSENGER);
                roleRepository.save(userRole);
            }

            //Create an Admin user
            User admin = userRepository.findByEmail("admin@gmail.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("admin@gmail.com")
                        .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setMobileNumber("9425094250")
                        .setRoles(Arrays.asList(adminRole));
                userRepository.save(admin);
            }

            //Create a passenger user
            User passenger = userRepository.findByEmail("passenger@gmail.com");
            if (passenger == null) {
                passenger = new User()
                        .setEmail("passenger@gmail.com")
                        .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFirstName("Mira")
                        .setLastName("Jane")
                        .setMobileNumber("8000110008")
                        .setRoles(Arrays.asList(userRole));
                userRepository.save(passenger);
            }
        };
    }
}
