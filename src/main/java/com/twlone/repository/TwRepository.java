package com.twlone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Tw;
import com.twlone.entity.User;

public interface TwRepository extends JpaRepository<Tw, Integer>{
    List<Tw> findByUser(User user);
}
