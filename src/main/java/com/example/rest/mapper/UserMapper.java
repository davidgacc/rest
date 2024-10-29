package com.example.rest.mapper;

import com.example.rest.dto.UserResponseDto;
import com.example.rest.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setCpf(user.getCpf());
        dto.setNome(user.getNome());
        dto.setDataNascimento(user.getDataNascimento().toLocalDate().toString()); // Adjust as needed
        dto.setRua(user.getRua());
        dto.setNumero(user.getNumero());
        dto.setComplemento(user.getComplemento());
        dto.setBairro(user.getBairro());
        dto.setCidade(user.getCidade());
        dto.setEstado(user.getEstado());
        dto.setCep(user.getCep());
        dto.setStatus(user.getStatus().name());
        return dto;
    }
}
