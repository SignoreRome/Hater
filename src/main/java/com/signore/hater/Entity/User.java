package com.signore.hater.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
@Table(name = "usr")
@NamedEntityGraph(
        name = "user-role-entity-graph",
        attributeNodes = {@NamedAttributeNode("roles")}
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Column(name = "password")
    private String password;

    @Transient
    private String password2;

    @Column(name = "active")
    private boolean active;

    @Email(message = "Email isn't correct")
    @NotBlank(message = "Email cannot be empty")
    @Column(name = "email")
    private String email;

    @Column(name = "activation_code")
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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
        return isActive();
    }
}
