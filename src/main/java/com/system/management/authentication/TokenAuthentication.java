package com.system.management.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.system.management.domain.entity.User;
import com.system.management.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TokenAuthentication implements AuthenticationProvider {

    UserRepository userRepository;

    PasswordEncoder passEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUserName(userName);
        if (user != null) {
            if(passEncoder.matches(password,user.getUserPass()))
                return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
            else
                throw new BadCredentialsException("Invalid Password");
        } else {
            throw new UsernameNotFoundException("Username not found");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
