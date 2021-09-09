package com.example.login10.Controller.User;

import com.example.login10.DTO.UserDTO;
import com.example.login10.Exception.UserEmailAlreadyRegistered;
import com.example.login10.Exception.UserNotFound;
import com.example.login10.Service.UserService;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{email}")
    public UserDTO returnUserByEmail(@PathVariable String email) throws UserNotFound {

        return userService.findUserByEmail(email);

    }

    @GetMapping
    public ResponseEntity<UserDetails> findByIdAuthenticationPrincipal(
                                                                       @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(userDetails);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody @Valid UserDTO userDTO) throws UserEmailAlreadyRegistered {
        return userService.save(userDTO);
    }
}
