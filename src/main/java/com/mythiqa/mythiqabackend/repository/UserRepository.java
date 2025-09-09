package com.mythiqa.mythiqabackend.repository;

import com.mythiqa.mythiqabackend.model.User;
import com.mythiqa.mythiqabackend.projection.user.UserDisplayNameProjection;
import com.mythiqa.mythiqabackend.projection.user.UserProfileImgProjection;
import com.mythiqa.mythiqabackend.projection.user.UserProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String userId);

    Optional<UserProfileProjection> findUserProfileByUsername(String username);

    Optional<UserProfileImgProjection> findUserProfileImgByUsername(String username);

    Optional<UserDisplayNameProjection> findDisplayNameByUsername(String username);
}
