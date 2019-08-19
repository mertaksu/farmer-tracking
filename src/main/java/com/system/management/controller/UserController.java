package com.system.management.controller;

import com.system.management.authentication.TokenAuthentication;
import com.system.management.domain.request.LoginRequest;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.LoginResponse;
import com.system.management.domain.response.UserResponse;
import com.system.management.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@AllArgsConstructor
@RestController
public class UserController {

    IUserService userService;

    TokenAuthentication tokenAuthentication;

    PasswordEncoder passEncoder;

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> saveUser(@RequestBody UserRequest userRequest) {
        int userId = userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @GetMapping(path = "/user/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Integer userId) {
        UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping(path = "/user/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserRequest userRequest) {
        Boolean response = userService.updateUser(userId, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        try {
            tokenAuthentication.authenticate(authentication);
            String userId = userService.getUserId(loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(new String(Base64.getEncoder().encode((loginRequest.getUsername() + ":" + loginRequest.getPassword()).getBytes())), userId, "SUCCESS"));
        } catch (BadCredentialsException b) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("", "", "Invalid Password"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("", "", "Username not found"));
        }
    }
}
