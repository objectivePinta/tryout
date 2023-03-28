package com.tryout.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.tryout.security.JWTAuthenticationFilter.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {
        var header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        var authenticationToken = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req, res);
    }

    UsernamePasswordAuthenticationToken getToken(String header) {
        try {
            var jwt = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.SECRET.getBytes()))
                    .build()
                    .verify(header.replace(TOKEN_PREFIX, ""));
            var roles = jwt.getClaim("roles").asList(String.class);
            var user = jwt.getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, JwtUserDetailsService.fromListOfRoles(roles));
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

}
