-- Acrescenta um restaurant_id à tabela address
ALTER TABLE address ADD COLUMN restaurant_id UUID;

ALTER TABLE address
  ADD CONSTRAINT fk_address_restaurant
  FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE;

CREATE INDEX IF NOT EXISTS idx_address_restaurant_id ON address(restaurant_id);

-- Garante que o endereço pertença a um usuário OU a um restaurante, mas não a ambos
ALTER TABLE address
  ADD CONSTRAINT chk_only_one_owner
  CHECK (
    (user_id IS NOT NULL AND restaurant_id IS NULL) OR
    (user_id IS NULL AND restaurant_id IS NOT NULL)
  );
