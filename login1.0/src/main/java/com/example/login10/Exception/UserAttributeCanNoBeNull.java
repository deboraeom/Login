package com.example.login10.Exception;

public class UserAttributeCanNoBeNull extends RuntimeException{
    public UserAttributeCanNoBeNull() {
        super("Preencha todosmos atributos");
    }
}
