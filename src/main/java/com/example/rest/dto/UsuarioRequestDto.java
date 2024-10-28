package com.example.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {

    @NotBlank(message = "CPF is mandatory")
    @Size(min = 11, max = 11, message = "CPF must be 11 digits")
    @Pattern(regexp = "\\d{11}", message = "CPF must contain only digits")
    private String cpf;

    @NotBlank(message = "Name is mandatory")
    private String nome;

    @NotBlank(message = "Date of birth is mandatory")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Date of birth must be in the format dd/MM/yyyy")
    private String dataNascimento; // Expecting input in dd/MM/yyyy format

    @NotBlank(message = "Street is mandatory")
    private String rua;

    @NotBlank(message = "Number is mandatory")
    private String numero;

    private String complemento; // Optional

    @NotBlank(message = "Neighborhood is mandatory")
    private String bairro;

    @NotBlank(message = "City is mandatory")
    private String cidade;

    @NotBlank(message = "State is mandatory")
    @Size(min = 2, max = 2, message = "State must be 2 characters")
    private String estado; // Expecting 2-character state abbreviation

    @NotBlank(message = "ZIP code is mandatory")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "ZIP code must be in the format XXXXX-XXX")
    private String cep; // Expecting format XXXXX-XXX

    private String usuarioCriacao; // Optional; can be set server-side
}
