package com.example.login10.domain;

import com.example.login10.Exception.UserAttributeCanNoBeNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name="User")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;


    @Transient
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public User(String name, String email, String password) throws UserAttributeCanNoBeNull {
        if(name.equals("") || email.equals("") || password.isEmpty()){

            throw new UserAttributeCanNoBeNull();
        }else {
            this.name = name;
            this.email = email;
            this.password = passwordEncoder.encode(password);
        }
    }



    public User(Long id, String name, String email, String password) throws UserAttributeCanNoBeNull{
        if(name.equals("") || email.equals("") || password.isEmpty()){

            throw new  UserAttributeCanNoBeNull();
        }else {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = passwordEncoder.encode(password);
        }
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {

        if(password.isEmpty()){
            throw new  UserAttributeCanNoBeNull();
        }else {
            this.password = passwordEncoder.encode(password);}
    }

    public void setName(String name) {
        if(name.isEmpty()){
            throw new  UserAttributeCanNoBeNull();
        }else {
            this.name = name;}
    }

    public void setEmail(String email) {
        if(email.isEmpty()){
            throw new  UserAttributeCanNoBeNull();
        }else {
            this.email = email;}
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authorities = "standard";
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
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