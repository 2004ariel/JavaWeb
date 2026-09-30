-- Script de criação do banco usado pela aplicação (ver src/main/resources/application.properties)
-- Uso: banco novo/vazio. Para uma base "restaurante" já existente com a tabela "users",
-- use migration_usuarios.sql em vez deste script.

CREATE DATABASE IF NOT EXISTS restaurante
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE restaurante;

-- Schema gerado pelo Hibernate (ddl-auto=update) a partir do UserModel com Lombok:
-- sem @Column(nullable=false)/unique=true no entity, nome/email/senha/tipo ficam
-- opcionais e o e-mail deixa de ter unicidade garantida pelo banco.
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    tipo ENUM('COMPRADOR', 'VENDEDOR', 'ADMIN'),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

-- created_at/updated_at não têm mais DEFAULT no banco (quem preenche é o Hibernate,
-- via @CreationTimestamp/@UpdateTimestamp, ao inserir pela aplicação), por isso o
-- INSERT direto via SQL precisa informar os dois.
INSERT INTO usuarios (nome, email, senha, tipo, created_at, updated_at) VALUES
    ('John Doe', 'john.doe@example.com', 'changeme123', 'COMPRADOR', NOW(), NOW()),
    ('Jane Smith', 'jane.smith@example.com', 'changeme123', 'VENDEDOR', NOW(), NOW()),
    ('Ariel Dornelles', 'ariel.dss@example.com', 'changeme123', 'ADMIN', NOW(), NOW());

-- Schema gerado pelo Hibernate (ddl-auto=update) a partir do BairroModel:
-- sem @Column no entity, todas as colunas ficam VARCHAR(255) opcionais e sem
-- constraints; cepInicial vira cep_inicial pela naming strategy padrão do Spring.
CREATE TABLE IF NOT EXISTS bairros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    cep_inicial VARCHAR(255)
);
