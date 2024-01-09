package com.projects.springsecuritydemo.repository;

import com.projects.springsecuritydemo.entities.Role;
import com.projects.springsecuritydemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<UserDetails> findByEmail(String username);

    User findByRole(Role role);
}
