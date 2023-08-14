package com.example.shatapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import com.example.shatapp.model.ChatMessage;
import com.example.shatapp.model.ChatMessageType;
import com.example.shatapp.model.User;
import com.example.shatapp.service.ChatRoomService;
import com.example.shatapp.service.UserService;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionSubscribeEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        String email = headerAccessor.getSubscriptionId();
       
        headerAccessor.getSessionAttributes().put("email", email);
        headerAccessor.getSessionAttributes().put("topic", destination);

        logger.info("Received a new subscription to: " + destination + ", from: " + email);

//        User user = this.userService.findByEmailAndFetchChatRooms(email).get();
       // this.chatRoomService.addUserToChatRoom(chatRoomId, user);
//
//        ChatMessage chatMessage = new ChatMessage(MessageType.JOIN,
//                                                  user.getName() + " joined the chat",
//                                                  email,
//                                                  user.getName(),
//                                                  user.getPicture(),
//                                                  null,
//                                                  null);
//                
//        messagingTemplate.convertAndSend(destination + "/" + user.getId(), chatMessage);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String destination = (String) headerAccessor.getSessionAttributes().get("topic");
//        String email = (String) headerAccessor.getSessionAttributes().get("email");
//        String chatRoomId = destination.substring(destination.lastIndexOf("/") + 1);
//
//        logger.info("Received a DISCONNECT to: " + destination + ", from: " + email);
//
//        User user = this.userService.findByEmailAndFetchChatRooms(email).get();
//        this.chatRoomService.removeUserFromChatRoom(chatRoomId, user);
//
//        ChatMessage chatMessage = new ChatMessage(MessageType.LEAVE,
//                                                  user.getName() + " left the chat",
//                                                  email,
//                                                  user.getName(),
//                                                  null,
//                                                  null,
//                                                  null);
//
//        messagingTemplate.convertAndSend(destination, chatMessage);
    }

}
