package com.myserv.api.rh.controller;

import com.myserv.api.rh.model.User;
import com.myserv.api.rh.model.UserDTO;
import com.myserv.api.rh.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/api/users/all")
    public List<UserDTO> getAllUsers() {
        return this.userService.findAllUsers();
    }

    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable String id) {
        this.userService.deleteById(id);
    }
    @GetMapping("/api/users/{id}")
    public Optional<UserDTO> getById(@PathVariable String id) {
        return  this.userService.getById(id);
    }
    @PutMapping("/api/users/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }
}
