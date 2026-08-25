-- Não encontrei script de criação no ProjetoI original; schema inferido
-- a partir das colunas usadas em ProdutoDAO (nome, preco, quantidade).
-- Ajuste se a tabela real no seu MySQL local for diferente.

CREATE DATABASE IF NOT EXISTS cantina;
USE cantina;

CREATE TABLE IF NOT EXISTS produto (
    id INT(11) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT(11) NOT NULL,
    PRIMARY KEY (id)
);

-- Aula 04: tabela de usuários para o login.
-- A coluna senha guarda o HASH bcrypt (60 caracteres), nunca a senha pura.
CREATE TABLE IF NOT EXISTS usuarios (
    id INT(11) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_email (email)
);

-- dados de exemplo, opcional
-- ATENÇÃO: rodar este arquivo de novo duplica estes 3 produtos.
INSERT INTO produto (nome, preco, quantidade) VALUES
    ('Coxinha', 8.00, 30),
    ('Refrigerante lata', 6.00, 50),
    ('Salgado assado', 7.50, 20);
