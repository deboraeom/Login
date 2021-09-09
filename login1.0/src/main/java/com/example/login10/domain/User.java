package com.example.login10.domain;

import com.example.login10.Exception.UserAttributeCanNoBeNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="User")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    public User(String name, String email, String password) throws UserAttributeCanNoBeNull {
        if(name.equals("") || email.equals("") || password.isEmpty()){

            throw new UserAttributeCanNoBeNull();
        }else {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }



    public User(Long id, String name, String email, String password) throws UserAttributeCanNoBeNull{
        if(name.equals("") || email.equals("") || password.isEmpty()){

            throw new  UserAttributeCanNoBeNull();
        }else {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        if(password.isEmpty()){
            throw new  UserAttributeCanNoBeNull();
        }else {
            this.password = password;}
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
    }}