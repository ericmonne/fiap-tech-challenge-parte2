-- Remover a coluna 'role' da tabela 'users'
ALTER TABLE users DROP COLUMN role;

-- Adicionar a coluna 'user_type_id' com tipo compatível com a PK da tabela user_type
ALTER TABLE users ADD COLUMN user_type_id BIGINT;

-- Criar a Foreign Key ligando user_type_id à tabela user_type(id)
ALTER TABLE users ADD CONSTRAINT fk_users_user_type FOREIGN KEY (user_type_id) REFERENCES user_type_id(id);