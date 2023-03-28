package com.tryout.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tryout.entities.User;
import com.tryout.enums.UserRoles;
import com.tryout.security.model.AuthenticationRequest;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.auth0.jwt.JWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SECRET = "SecretKeyToGenJWTs";
    private final Long EXPIRATION_TIME = 864000000L; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ROLES_CLAIM_NAME = "roles";
    public static final String FULLNAME_CLAIM_NAME = "fullname";
    public static final String EMAIL_VERIFICATION_ID = "emailVerificationId";

    public static final String SIGN_UP_URL = "/register";
    public static final String FACEBOOK_OAUTH = "/facebook/**";
    AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        AuthenticationRequest user = getFromHeader(request);
//        try {
//            user = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            authorityList = JwtUserDetailsService.fromListOfRoles(user.getRoles());
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        authorityList
                )
        );
    }

    public AuthenticationRequest getFromHeader(HttpServletRequest httpRequest) {
        final String authorization = httpRequest.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            return new AuthenticationRequest(values[0], values[1], List.of(UserRoles.NO_ROLE.toString()));
        } else {
            throw new AuthenticationServiceException("wrong.credentials");
        }
    }

    @Override
    public void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) {
        User user = (User) authResult.getPrincipal();

        String[] roles = user.getAuthoritiesAsArray();

        var jwtBuilder = JWT.create().withSubject(user.getUsername())
                .withArrayClaim(ROLES_CLAIM_NAME, roles)
                .withClaim(FULLNAME_CLAIM_NAME, user.getName());
//        if (!user.hasEmailVerified) {
//            jwtBuilder.withClaim(EMAIL_VERIFICATION_ID, user.emailVerificationId)
//        }
        var token = jwtBuilder.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
