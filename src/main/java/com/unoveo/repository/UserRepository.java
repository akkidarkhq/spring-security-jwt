package com.unoveo.repository;

import com.unoveo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Bean
    Optional<User> findByUsername(String username);

    @Bean
    boolean existsByUsername(String username);

    @Bean
    boolean existsByEmail(String email);
}
