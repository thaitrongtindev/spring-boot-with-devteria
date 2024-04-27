package com.devteria.jpaapi.controller;

import com.devteria.jpaapi.dto.request.ApiResponse;
import com.devteria.jpaapi.dto.request.UserCreationRequest;
import com.devteria.jpaapi.dto.request.UserUpdateRequest;
import com.devteria.jpaapi.dto.request.response.UserResponse;
import com.devteria.jpaapi.entity.User;
import com.devteria.jpaapi.service.UserService;
import jakarta.transaction.UserTransaction;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Setter
@Getter
@Data
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/getString")
    public String getString() {
        return "Hello";
    }

    @Autowired
    UserService userService;

    @PostMapping("")
    ApiResponse<User> createUser(@Valid @RequestBody UserCreationRequest request) {
        System.out.println(request.toString());
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") Long userID) {
        return userService.getUserById(userID);
    }

    @PutMapping("/update/{userId}")
    public UserResponse updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUserById(request, userId);
    }
//    @PutMapping("/update/{userId}")
//    public User updateUser(@PathVariable("userId") Long userId, @RequestBody UserCreationRequest request) {
//        return userService.updateUserById(request, userId);
//    }


    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
    }
}
