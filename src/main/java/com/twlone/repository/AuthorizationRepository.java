package com.twlone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Authorization;

public interface AuthorizationRepository extends JpaRepository<Authorization, Integer>{
    Optional<Authorization> findByUserUserIdEquals(String userId);
}
