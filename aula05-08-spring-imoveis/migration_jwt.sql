-- Migração manual para a atividade de JWT.
-- A coluna "tipo" é ENUM no MySQL e ddl-auto=update não altera os valores aceitos,
-- então o novo valor USER precisa ser adicionado na mão.
-- As senhas antigas estão em texto puro e NÃO funcionam com bcrypt: recrie esses usuários
-- via POST /public/register (ou PUT /users/{id} com uma nova senha, como ADMIN).

USE restaurante;

ALTER TABLE usuarios
    MODIFY COLUMN tipo ENUM('COMPRADOR', 'VENDEDOR', 'ADMIN', 'USER') NOT NULL DEFAULT 'USER';
