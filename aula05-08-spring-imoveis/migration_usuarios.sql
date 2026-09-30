-- Migração manual: renomeia "users" para "usuarios" e adiciona as colunas do novo schema.
-- Rode este script ANTES de subir a aplicação com o UserModel atualizado.
-- ddl-auto=update não renomeia tabelas/colunas, por isso o ajuste é feito na mão.
-- Só mexe em "usuarios" — não toca em imoveis, fotos_imoveis, tipos_imoveis, bairros.

USE restaurante;

ALTER TABLE users RENAME TO usuarios;

ALTER TABLE usuarios RENAME COLUMN name TO nome;

-- Colunas novas com DEFAULT para não quebrar as linhas já existentes (John Doe, Jane Smith, Ariel Dornelles).
ALTER TABLE usuarios
    ADD COLUMN senha VARCHAR(255) NOT NULL DEFAULT 'changeme123',
    ADD COLUMN tipo ENUM('COMPRADOR', 'VENDEDOR', 'ADMIN') NOT NULL DEFAULT 'COMPRADOR',
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Ajuste os valores reais de senha/tipo dos usuários existentes conforme necessário, por exemplo:
-- UPDATE usuarios SET tipo = 'VENDEDOR' WHERE email = 'jane.smith@example.com';
-- UPDATE usuarios SET tipo = 'ADMIN' WHERE email = 'ariel.dss@example.com';

-- Opcional, depois de conferir os dados: remove os defaults de senha/tipo
-- para forçar que todo novo INSERT informe esses valores explicitamente.
-- ALTER TABLE usuarios ALTER COLUMN senha DROP DEFAULT;
-- ALTER TABLE usuarios ALTER COLUMN tipo DROP DEFAULT;
