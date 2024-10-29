package com.example.rest.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private String cpf;
    private String nome;
    private String dataNascimento; // Consider storing this as a String for response
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String status;
}
