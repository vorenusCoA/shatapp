package com.example.shatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shatapp.model.FriendshipInvite;
import com.example.shatapp.model.FriendshipInviteID;

public interface FriendshipInviteRepository extends JpaRepository<FriendshipInvite, FriendshipInviteID> {

}
