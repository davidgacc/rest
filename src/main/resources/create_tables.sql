CREATE TABLE Usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATETIME NOT NULL,
    rua VARCHAR(255),
    numero VARCHAR(50),
    complemento VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(100),
    cep VARCHAR(20),
    data_criacao DATETIME,
    usuario_criacao VARCHAR(255),
    data_atualizacao DATETIME,
    usuario_atualizacao VARCHAR(255),
    data_remocao DATETIME,
    usuario_remocao VARCHAR(255)
);

-- create index
CREATE INDEX idx_cpf ON usuarios (cpf);