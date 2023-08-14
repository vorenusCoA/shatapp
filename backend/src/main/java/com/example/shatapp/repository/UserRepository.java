package com.example.shatapp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.shatapp.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM ChatRoom cr JOIN cr.users u WHERE cr.id = :chatRoomId")
    List<User> findUsersInChatRoom(@Param("chatRoomId") UUID chatRoomId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.chatRooms WHERE u.email = :email")
    Optional<User> findByEmailAndFetchChatRooms(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.friends WHERE u.email = :email")
    Optional<User> findByEmailAndFetchFriends(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.invitesReceived WHERE u.email = :email")
    Optional<User> findByEmailAndFetchFriendshipInvites(@Param("email") String userEmail);

}
