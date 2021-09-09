package com.example.login10.Domain;

import com.example.login10.Exception.UserAttributeCanNoBeNull;
import com.example.login10.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Test
    void instancesUsersuccessfully(){
        String name="Débora";
        String email= "debora@gmail.com";
        String password ="password";

        assertAll(
                ()->assertDoesNotThrow(()->new User(name, email, password)),
                ()-> assertEquals(name,new User(name, email, password).getName()),
                ()-> assertEquals(email,new User(name, email, password).getEmail()),
                ()-> assertEquals(password,new User(name, email, password).getPassword())
        );

    }

    @Test
    void tryToInstanceUserWhithNamePassowordOrEmailEmpty(){
        String name="Débora";
        String email= "debora@gmail.com";
        String password ="password";

        assertAll(
                ()->assertThrows( UserAttributeCanNoBeNull.class,()->new User("", email, password)),
                ()->assertThrows(UserAttributeCanNoBeNull.class,()->new User(name, "", password)),
                ()->assertThrows(UserAttributeCanNoBeNull.class,()->new User(name, email, ""))
        );


    }

    @Test
    void veryfyIfSettersIsWorkingUsersuccessfully(){
        String name="Débora";
        String email= "debora@gmail.com";
        String password ="password";
        User user = new User("name", "email", "password");
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        assertAll(

                ()-> assertEquals(name, user.getName()),
                ()-> assertEquals(email,user.getEmail()),
                ()-> assertEquals(password,user.getPassword())
        );

    }

    @Test
    void tryToSetANullAttributeNamePassowordOrEmailEmptyThenThrows(){
        String name="Débora";
        String email= "debora@gmail.com";
        String password ="password";
        User user = new User("name", "email", "password");

        assertAll(
                ()->assertThrows( UserAttributeCanNoBeNull.class,()->user.setName("")),
                ()->assertThrows(UserAttributeCanNoBeNull.class,()->user.setEmail("")),
                ()->assertThrows(UserAttributeCanNoBeNull.class,()->user.setPassword(""))
        );


    }



}
