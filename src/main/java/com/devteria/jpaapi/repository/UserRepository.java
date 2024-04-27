package com.devteria.jpaapi.repository;

import com.devteria.jpaapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // co the viet them cac phuong thuc
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}