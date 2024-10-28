package com.example.rest.service;

import com.example.rest.exception.UserAlreadyExistsException;
import com.example.rest.model.Status;
import com.example.rest.model.Usuario;
import com.example.rest.dto.UsuarioRequestDto;
import com.example.rest.repository.UsuarioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    public Usuario getUserByCpf(String cpf) {
        logger.debug("Searching for user with CPF: {}", cpf);
        return usuarioRepository.findByCpf(cpf);
    }

    public Usuario criarUsuario(UsuarioRequestDto usuarioRequest) {

        logger.debug("Attempting to create user with CPF: {}", usuarioRequest.getCpf());

        // Check if a user with the same CPF already exists
        if (usuarioRepository.findByCpf(usuarioRequest.getCpf()) != null) {
            logger.warn("User with CPF: {} already exists", usuarioRequest.getCpf());
            throw new UserAlreadyExistsException(usuarioRequest.getCpf());
        }

        // Convert dataNascimento from String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(usuarioRequest.getDataNascimento(), formatter);


        // Create a new Usuario object
        Usuario usuario = Usuario.builder()
            .cpf(usuarioRequest.getCpf())
            .nome(usuarioRequest.getNome())
            .dataNascimento(dataNascimento.atStartOfDay()) // Convert LocalDate to LocalDateTime
            .rua(usuarioRequest.getRua())
            .numero(usuarioRequest.getNumero())
            .complemento(usuarioRequest.getComplemento())
            .bairro(usuarioRequest.getBairro())
            .cidade(usuarioRequest.getCidade())
            .estado(usuarioRequest.getEstado())
            .cep(usuarioRequest.getCep())
            .status(Status.ATIVO)
            .dataCriacao(LocalDateTime.now())
            .usuarioCriacao(usuarioRequest.getUsuarioCriacao()) // Set user creation
            .build();

        // Save the user
        Usuario savedUser  = usuarioRepository.save(usuario);
        logger.info("User created successfully with ID: {}", savedUser.getId());
        return savedUser;
    }


}
