package com.example.demo.notes.controller;

import com.example.demo.notes.entity.User;
import com.example.demo.notes.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class UserController {

    private final UserService userSercice;

    public UserController(UserService userSercice) {
        this.userSercice = userSercice;
    }

    // Create new User
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User userRequest) {
        // Call the NoteService to handle user creation
        User createdUser = userSercice.createUser(
        		userRequest.getUsername(),
        		userRequest.getPassword());
        return ResponseEntity.ok(createdUser);
    }
    
    // Get all Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userSercice.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    // Update a User by ID
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userRequest
    ) {
        Optional<User> optionalUser = userSercice.getAllUsers()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User userToUpdate = optionalUser.get();
        userToUpdate.setUsername(userRequest.getUsername());
        userToUpdate.setPassword(userRequest.getPassword());
        userToUpdate.setRoles(userRequest.getRoles());

        // Persist the updated user
        User updatedUser = userSercice.updateUser(userToUpdate);

        return ResponseEntity.ok(updatedUser);
    }

    // Delete a User by ID
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userSercice.getAllUsers()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userSercice.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
