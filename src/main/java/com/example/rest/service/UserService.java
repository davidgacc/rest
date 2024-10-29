package com.example.rest.service;

import com.example.rest.dto.UserRequestDto;
import com.example.rest.exception.UserAlreadyExistsException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.model.Status;
import com.example.rest.model.User;
import com.example.rest.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUserByCpf(String cpf) {
        logger.debug("Searching for user with CPF: {}", cpf);
        return userRepository.findByCpf(cpf);
    }

    public User createUser(UserRequestDto usuarioRequest) {

        logger.debug("Attempting to create user with CPF: {}", usuarioRequest.getCpf());

        // Check if a user with the same CPF already exists
        if (userRepository.findByCpf(usuarioRequest.getCpf()) != null) {
            logger.warn("User with CPF: {} already exists", usuarioRequest.getCpf());
            throw new UserAlreadyExistsException(usuarioRequest.getCpf());
        }

        // Convert dataNascimento from String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(usuarioRequest.getDataNascimento(), formatter);


        // Create a new Usuario object
        User user = User.builder()
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
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Transactional
    public void deleteUserByCpf(String cpf) {
        User usuario = userRepository.findByCpf(cpf);
        if (usuario == null) {
            logger.warn("User with CPF: {} not found for deletion", cpf);
            throw new UserNotFoundException();
        }

        userRepository.delete(usuario);
        logger.info("User with CPF: {} deleted successfully", cpf);
    }

    @Transactional
    public User updateUser(UserRequestDto usuarioRequest) {

        String cpf = usuarioRequest.getCpf();

        User user = userRepository.findByCpf(cpf);
        if (user == null) {
            logger.warn("User with CPF: {} not found for update", cpf);
            throw new UserNotFoundException("Usuário não encontrado com o CPF fornecido.");
        }

        // Convert dataNascimento from String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse(usuarioRequest.getDataNascimento(), formatter);

        // Update fields
        user.setNome(usuarioRequest.getNome());
        user.setDataNascimento(dataNascimento.atStartOfDay());
        user.setRua(usuarioRequest.getRua());
        user.setNumero(usuarioRequest.getNumero());
        user.setComplemento(usuarioRequest.getComplemento());
        user.setBairro(usuarioRequest.getBairro());
        user.setCidade(usuarioRequest.getCidade());
        user.setEstado(usuarioRequest.getEstado());
        user.setCep(usuarioRequest.getCep());
        user.setStatus(usuarioRequest.getStatus());

        userRepository.save(user); // Save the updated user

        logger.info("User with CPF: {} updated successfully", cpf);
        return user;
    }

    @Transactional
    public User updatePartialUser(UserRequestDto usuarioRequest) {

        // Get the CPF from the request DTO
        String cpf = usuarioRequest.getCpf();

        User usuario = userRepository.findByCpf(cpf);
        if (usuario == null) {
            logger.warn("User with CPF: {} not found for update", cpf);
            throw new UserNotFoundException("Usuário não encontrado com o CPF fornecido.");
        }


        // Update fields only if they are present in the request
        if (usuarioRequest.getNome() != null) {
            usuario.setNome(usuarioRequest.getNome());
        }
        if (usuarioRequest.getDataNascimento() != null) {
            // Convert dataNascimento from String to LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(usuarioRequest.getDataNascimento(), formatter);
            usuario.setDataNascimento(dataNascimento.atStartOfDay());
        }
        if (usuarioRequest.getRua() != null) {
            usuario.setRua(usuarioRequest.getRua());
        }
        if (usuarioRequest.getNumero() != null) {
            usuario.setNumero(usuarioRequest.getNumero());
        }
        if (usuarioRequest.getComplemento() != null) {
            usuario.setComplemento(usuarioRequest.getComplemento());
        }
        if (usuarioRequest.getBairro() != null) {
            usuario.setBairro(usuarioRequest.getBairro());
        }
        if (usuarioRequest.getCidade() != null) {
            usuario.setCidade(usuarioRequest.getCidade());
        }
        if (usuarioRequest.getEstado() != null) {
            usuario.setEstado(usuarioRequest.getEstado());
        }
        if (usuarioRequest.getCep() != null) {
            usuario.setCep(usuarioRequest.getCep());
        }
        if (usuarioRequest.getStatus() != null) {
            usuario.setStatus(usuarioRequest.getStatus());
        }

        userRepository.save(usuario); // Save the updated user

        logger.info("User with CPF: {} updated successfully", cpf);
        return usuario;
    }
}
