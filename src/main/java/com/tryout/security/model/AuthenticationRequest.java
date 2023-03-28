package com.tryout.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;

    private String password;

    private List<String> roles;
}