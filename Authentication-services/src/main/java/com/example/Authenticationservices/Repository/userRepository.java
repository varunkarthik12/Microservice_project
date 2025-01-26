package com.example.Authenticationservices.Repository;

import com.example.Authenticationservices.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserNameOrEmail(String username, String Email);

}
