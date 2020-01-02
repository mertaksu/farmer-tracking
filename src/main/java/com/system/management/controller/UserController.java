package com.system.management.controller;

import com.system.management.domain.request.UserRequest;
import com.system.management.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @DeleteMapping(path = "/user/{userId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable("userId") Integer userId){
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            log.error("Exception: ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
