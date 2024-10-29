package com.example.rest.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado com o CPF fornecido");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
