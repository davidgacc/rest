package com.example.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDateTime dataNascimento;

    // Address fields moved directly to Usuario
    @Column(nullable = false)
    private String rua;

    @Column(nullable = false)
    private String numero;

    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime dataCriacao;
    private String usuarioCriacao;
    private LocalDateTime dataAtualizacao;
    private String usuarioAtualizacao;
    private LocalDateTime dataRemocao;
    private String usuarioRemocao;
}
