package com.twlone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
