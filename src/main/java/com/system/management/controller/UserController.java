package com.system.management.controller;

import com.system.management.authentication.TokenAuthentication;
import com.system.management.domain.request.RegisterRequest;
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

    TokenAuthentication tokenAuthentication;

    @PostMapping(path = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody RegisterRequest registerRequest) {
        int userId = userService.saveUser(registerRequest);
        if(userId==-1) return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
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

    /*
    @PostMapping(path = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(HttpServletResponse response, @RequestBody LoginRequest loginRequest) throws AuthenticationException {
        log.info("Login request received for username:{}",loginRequest.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
        Authentication auth = tokenAuthentication.authenticate(authentication);
        if(auth!=null) {
            log.info("Login request succeed for username:{}",loginRequest.getUsername());
            response.addHeader("x-auth",new String(Base64.getEncoder().encode((loginRequest.getUsername()+":"+loginRequest.getPassword()).getBytes())));
            return new LoginResponse(200,new String(Base64.getEncoder().encode((loginRequest.getUsername()+":"+loginRequest.getPassword()).getBytes())));
        }
        else {
            log.warn("Invalid username or password");
            throw new BadCredentialsException("Invalid username or password");
        }
    }
     */

}
