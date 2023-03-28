package com.tryout.rest.dto;

import com.tryout.entities.User;
import com.tryout.enums.UserRoles;
import com.tryout.enums.UserType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;

    private String username;

    private String email;
    private String password;

    public User toUser() {
        return User.builder()
                .name(firstName + " " + lastName)
                .username(username)
                .email(email)
                .hasConfirmedEmail(false)
                .userType(UserType.CUSTOMER)
                .roles(List.of(UserRoles.NO_ROLE))
                .password(password)
                .build();
    }

}
