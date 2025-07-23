-- remove a coluna role da tabela de usuarios
ALTER TABLE users DROP COLUMN role;

-- adiciona a coluna user_type_id na tabela users
ALTER TABLE users ADD COLUMN user_type_id BIGINT;

-- criar a Foreign Key ligando à tabela 'user_type'
ALTER TABLE users ADD CONSTRAINT fk_user_type FOREIGN KEY (user_type_id) REFERENCES user_type(id);