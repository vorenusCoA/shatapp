package com.example.shatapp.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.shatapp.model.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    @Query(value = "SELECT cr FROM ChatRoom cr LEFT JOIN FETCH cr.users WHERE cr.id = :chatRoomId")
    Optional<ChatRoom> findByIdAndFetchUsers(@Param("chatRoomId") UUID chatRoomId);

}
