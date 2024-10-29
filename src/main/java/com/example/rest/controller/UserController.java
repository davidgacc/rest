package com.example.rest.controller;

import com.example.rest.dto.UserRequestDto;
import com.example.rest.dto.UserResponseDto;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.mapper.UserMapper;
import com.example.rest.model.User;
import com.example.rest.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper usuarioMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUsuario(@Valid @RequestBody UserRequestDto userRequestDto) {
        logger.info("Received request to create user with CPF: {}", userRequestDto.getCpf());
        User createdUser = userService.createUser(userRequestDto);
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UserResponseDto> getUserByCpf(@PathVariable String cpf) {
        logger.info("Received request to get user with CPF: {}", cpf);

        User user = userService.getUserByCpf(cpf);

        if (user != null) {
            logger.info("User found: {}", user);
            UserResponseDto responseDto = usuarioMapper.toResponseDto(user);
            return ResponseEntity.ok(responseDto);
        } else {
            logger.warn("User with CPF: {} not found", cpf);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto usuarioRequest) {
        logger.info("Received request to update user with CPF: {}", usuarioRequest.getCpf());

        try {
            User updatedUser = userService.updateUser(usuarioRequest);
            UserResponseDto responseDto = usuarioMapper.toResponseDto(updatedUser);
            return ResponseEntity.ok(responseDto); // 200 OK
        } catch (UserNotFoundException e) {
            logger.warn("User update failed: {}", e.getMessage());
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PatchMapping
    public ResponseEntity<UserResponseDto> updateUserPartial(@RequestBody UserRequestDto usuarioRequest) {
        logger.info("Received request to partially update user with CPF: {}", usuarioRequest.getCpf());

        try {
            User updatedUser = userService.updatePartialUser(usuarioRequest);
            UserResponseDto responseDto = usuarioMapper.toResponseDto(updatedUser);
            return ResponseEntity.ok(responseDto); // 200 OK
        } catch (UserNotFoundException e) {
            logger.warn("User update failed: {}", e.getMessage());
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteUser(@PathVariable String cpf) {
        logger.info("Received request to delete user with CPF: {}", cpf);
        try {
            userService.deleteUserByCpf(cpf);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (UserNotFoundException e) {
            logger.warn("User deletion failed: {}", e.getMessage());
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
