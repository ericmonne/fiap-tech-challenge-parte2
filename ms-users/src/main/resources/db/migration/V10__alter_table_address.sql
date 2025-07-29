-- Acrescenta um restaurant_id à tabela address
ALTER TABLE address ADD COLUMN restaurant_id UUID;
ALTER TABLE address ADD CONSTRAINT fk_address_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id);
CREATE INDEX IF NOT EXISTS idx_address_restaurant_id ON address(restaurant_id);

--Não permite que o mesmo endereço tenha um user e um restaurant associados para evitar inconsistências de dados
ALTER TABLE address ADD CONSTRAINT chk_only_one_owner
CHECK (
    (user_id IS NOT NULL AND restaurant_id IS NULL) OR
    (user_id IS NULL AND restaurant_id IS NOT NULL)
);