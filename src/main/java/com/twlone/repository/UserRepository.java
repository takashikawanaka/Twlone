package com.twlone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twlone.dto.MiniUserDTO;
import com.twlone.dto.UserDTO;
import com.twlone.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(u) = 1 FROM User u WHERE u.userId = ?1")
    Boolean existsByUserId(String userId);

    @Query("SELECT new com.twlone.dto.UserDTO(u.id, u.userId, u.name, u.description, u.icon, u.back, SIZE(u.followingList), SIZE(u.followerList))"
            + "FROM User u WHERE u.userId = ?1")
    Optional<UserDTO> findUserDTOByUser(String userId);

    @Query("SELECT COUNT(f) = 1 FROM Follow f WHERE f.sourceUser = ?1 AND f.targetUser.id = ?2")
    Boolean existsFollowBySourceUserAndTargetUserDTO(User sourceUser, Integer targetUser);

    @Query("SELECT COUNT(u) FROM User u")
    Integer countUser();

    @Query("SELECT new com.twlone.dto.MiniUserDTO(u.userId, u.name, u.icon) FROM User u WHERE u.id IN ?1")
    List<MiniUserDTO> findUserListByRandomId(List<Integer> idList);
}
