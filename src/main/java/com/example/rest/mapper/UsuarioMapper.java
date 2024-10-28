package com.example.rest.mapper;

import com.example.rest.dto.UsuarioResponseDto;
import com.example.rest.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDto toResponseDto(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setCpf(usuario.getCpf());
        dto.setNome(usuario.getNome());
        dto.setDataNascimento(usuario.getDataNascimento().toLocalDate().toString()); // Adjust as needed
        dto.setRua(usuario.getRua());
        dto.setNumero(usuario.getNumero());
        dto.setComplemento(usuario.getComplemento());
        dto.setBairro(usuario.getBairro());
        dto.setCidade(usuario.getCidade());
        dto.setEstado(usuario.getEstado());
        dto.setCep(usuario.getCep());
        dto.setStatus(usuario.getStatus().name());
        return dto;
    }
}
