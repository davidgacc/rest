package com.example.rest.controller;

import com.example.rest.dto.UsuarioResponseDto;
import com.example.rest.mapper.UsuarioMapper;
import com.example.rest.model.Usuario;
import com.example.rest.dto.UsuarioRequestDto;
import com.example.rest.service.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UsuarioService userService;
    @Autowired
    private UsuarioMapper usuarioMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequest) {
        logger.info("Received request to create user with CPF: {}", usuarioRequest.getCpf());
        Usuario createdUser  = userService.criarUsuario(usuarioRequest);
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UsuarioResponseDto> getUserByCpf(@PathVariable String cpf) {
        logger.info("Received request to get user with CPF: {}", cpf);

        Usuario usuario = userService.getUserByCpf(cpf);

        if (usuario != null) {
            logger.info("User found: {}", usuario);
            UsuarioResponseDto responseDto = usuarioMapper.toResponseDto(usuario);
            return ResponseEntity.ok(responseDto);
        } else {
            logger.warn("User with CPF: {} not found", cpf);
            return ResponseEntity.notFound().build();
        }
    }
}
