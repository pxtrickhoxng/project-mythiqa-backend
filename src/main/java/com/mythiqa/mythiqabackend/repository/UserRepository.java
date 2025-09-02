package com.mythiqa.mythiqabackend.repository;

import com.mythiqa.mythiqabackend.dto.response.UserProfileDTO;
import com.mythiqa.mythiqabackend.dto.response.UserProfileImgDTO;
import com.mythiqa.mythiqabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String userId);

    @Query("SELECT new com.mythiqa.mythiqabackend.dto.response.UserProfileDTO(u.username, u.description, u.profileBackgroundImgUrl, u.userProfileUrl, u.createdAt) FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<UserProfileDTO> findUserProfileByUsername(@Param("username") String username);

    @Query("SELECT new com.mythiqa.mythiqabackend.dto.response.UserProfileImgDTO(u.userProfileUrl) FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<UserProfileImgDTO> findUserProfileImgByUsername(@Param("username") String username);

}
