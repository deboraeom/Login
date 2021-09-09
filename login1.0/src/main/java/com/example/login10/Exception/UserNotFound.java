package com.example.login10.Exception;

import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFound  extends Exception{
    public UserNotFound(String message) {
        super(message);
    }


}
