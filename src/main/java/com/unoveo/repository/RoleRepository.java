package com.unoveo.repository;

import com.unoveo.model.Role;
import com.unoveo.model.RoleName;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Bean
    Optional<Role> findByName(RoleName roleName);
}
