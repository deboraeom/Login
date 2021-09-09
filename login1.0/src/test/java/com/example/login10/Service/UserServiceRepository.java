package com.example.login10.Service;

import com.example.login10.DTO.UserDTO;
import com.example.login10.Exception.UserEmailAlreadyRegistered;
import com.example.login10.Exception.UserNotFound;
import com.example.login10.Repository.UserRepository;
import com.example.login10.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceRepository {

    private static final String name="Débora";
    private static final String email= "debora@gmail.com";
    private static final String password ="password";
    private static final String message = "Usuário Salvo com sucesso";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void saveUserSuccessfully(){

        UserDTO userDTO = new UserDTO(1L,name, email, password);
        User user = new User(name, email, password);

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        String returnedMessage = userService.save(userDTO);
        assertEquals(message, returnedMessage);

    }

    @Test
    void tryToSaveUserButEmailAlreadyRegisteredReturnThenException(){

        UserDTO userDTO = new UserDTO(1L,name, email, password);
        User user = new User(name, email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        assertThrows(UserEmailAlreadyRegistered.class, ()->userService.save(userDTO));
    }


    @Test
    void tryToSaveUserButNotSendAllAttributesThenReturnException(){
        User user = new User(name, email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        assertAll(
                ()-> assertEquals(name, userService.findUserByEmail(email).getName()),
                ()-> assertEquals(email,userService.findUserByEmail(email).getEmail()),
                ()-> assertEquals(password,userService.findUserByEmail(email).getPassword())
        );

    }


    @Test
    void returnTrueWhenDoesNotHasAUserEmailRegistered(){
        User user = new User(name, email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertAll(
                ()-> assertTrue(userService.verifyIfEmailIsRegistered(email)));
    }

    @Test
    void returnFalseWhenHasAUserEmailRegistered(){
        User user = new User(name, email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        assertAll(
                ()-> assertFalse(userService.verifyIfEmailIsRegistered(email)));
    }

    @Test
    void getAUserByEmailSuccessfully(){
        User user = new User(name, email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        assertAll(
                ()-> assertEquals(name, userService.findUserByEmail(email).getName()),
                ()-> assertEquals(email,userService.findUserByEmail(email).getEmail()),
                ()-> assertEquals(password,userService.findUserByEmail(email).getPassword())
        );
    }

    @Test
    void whenTryToGetAUserByEmailThatDoesNotExistReturnException(){
        User user = new User(name, email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UserNotFound.class, ()->userService.findUserByEmail(email));
}}
