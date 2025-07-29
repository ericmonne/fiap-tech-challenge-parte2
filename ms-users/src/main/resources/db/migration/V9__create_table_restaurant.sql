-- Criar tabela restaurants
CREATE TABLE restaurants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    cuisine_type VARCHAR(100) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_restaurant_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
