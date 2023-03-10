package com.twlone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

}
