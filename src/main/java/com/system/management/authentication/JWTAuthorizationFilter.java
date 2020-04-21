package com.system.management.authentication;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String SECRET = "Y@lm@nTar!m";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if(header==null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken jwtToken = createJWTToken(request);
        SecurityContextHolder.getContext().setAuthentication(jwtToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken createJWTToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String userId = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (userId != null) {
                request.setAttribute("userId",userId);
                return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
