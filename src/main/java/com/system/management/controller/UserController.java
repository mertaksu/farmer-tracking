package com.system.management.controller;

import com.system.management.domain.request.UserRequest;
import com.system.management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class UserController {

    UserService userService;

    @PostMapping(path = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> saveUser(@RequestBody UserRequest userRequest) {
        int userId = userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @PutMapping(path = "/user/{userId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateUser(@PathVariable("userId") Integer userId,@RequestBody UserRequest userRequest) {
        Boolean response = userService.updateUser(userId,userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
