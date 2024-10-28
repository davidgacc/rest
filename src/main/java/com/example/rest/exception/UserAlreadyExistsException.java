package com.example.rest.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String cpf) {
        super("Usuário já existe com o CPF fornecido: " + cpf);
    }
}
