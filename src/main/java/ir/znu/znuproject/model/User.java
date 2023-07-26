package ir.znu.znuproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

import ir.znu.znuproject.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDate expireDate;
    @Column(nullable = false)
    private String name;
    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String password, LocalDate expireDate, Role role, String name) {
        this.username = username;
        this.password = password;
        this.expireDate = expireDate;
        this.role = role;
        this.name = name;
    }

    @PrePersist
    private void init() {
        this.role = Role.USER;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
