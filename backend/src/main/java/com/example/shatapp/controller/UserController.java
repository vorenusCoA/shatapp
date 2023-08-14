package com.example.shatapp.controller;

import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shatapp.model.User;
import com.example.shatapp.service.UserService;

@RestController
@RequestMapping(path = "api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User save(@RequestBody User user) {

        Optional<User> optUser = this.userService.findByEmail(user.getEmail());
        if (optUser.isPresent()) {
            return optUser.get();
        }

        return this.userService.save(user);
    }

    @GetMapping("/friend")
    private ResponseEntity<Set<User>> getFriends(JwtAuthenticationToken principal) {
        String userEmail = principal.getToken().getClaim("email");
        User user = this.userService.findByEmailAndFetchFriends(userEmail).get();
        return ResponseEntity.ok(user.getFriends());
    }



}
