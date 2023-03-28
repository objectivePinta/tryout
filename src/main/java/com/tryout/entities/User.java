package com.tryout.entities;

import com.tryout.enums.UserRoles;
import com.tryout.enums.UserType;
import com.tryout.enums.mapping.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "user_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String username;
    private String email;

    private String password;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private UserType userType;

    @Column(name = "has_confirmed_email")
    private boolean hasConfirmedEmail;

    @Type(
            type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = ListArrayType.SQL_ARRAY_TYPE,
                            value = "user_role"
                    )
            }
    )
    @Column(name = "roles", columnDefinition = "user_role[]")
    private List<UserRoles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> (GrantedAuthority) role::toString).collect(Collectors.toList());
    }

    public String[] getAuthoritiesAsArray() {
        String[] roles = new String[this.roles.size()];
        for (int i = 0; i < this.roles.size(); i++) {
            roles[i] = this.roles.get(i).toString();
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
