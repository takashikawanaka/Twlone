package com.twlone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUserId(String userId);
}
