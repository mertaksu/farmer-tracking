package com.system.management.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.system.management.domain.entity.User;
import com.system.management.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TokenAuthentication implements AuthenticationProvider{

	UserRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	  String userName =	authentication.getName();
	  String password = authentication.getCredentials().toString();
	  List<GrantedAuthority> authList = new ArrayList<>();
	  
	  List<User> user = userRepository.findByUserNameAndUserPass(userName, password);
	  if(user!=null && user.size()>0) {
		return new UsernamePasswordAuthenticationToken(userName,password,authList);
	  } else {
	  	return null;
	  }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
