package com.example.shatapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.shatapp.model.Message;
import com.example.shatapp.model.MessageID;
import com.example.shatapp.model.User;

public interface MessageRepository extends JpaRepository<Message, MessageID> {

    @Query("SELECT m FROM Message m WHERE m.id.fromUser = :user OR m.id.toUser = :user")
    List<Message> findAllMessagesFromAndToUser(@Param("user") User user);

}
