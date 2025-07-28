-- Ativa extensão para gerar UUIDs
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Tabela de Restaurantes
CREATE TABLE restaurants (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name TEXT NOT NULL,
  owner_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela de Endereço do Restaurante (1:1)
CREATE TABLE restaurant_addresses (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  restaurant_id UUID NOT NULL UNIQUE REFERENCES restaurants(id) ON DELETE CASCADE,
  street TEXT NOT NULL,
  city TEXT NOT NULL,
  state TEXT NOT NULL,
  zip TEXT NOT NULL
);

-- Tipos de Cozinha (domínio aberto)
CREATE TABLE cuisine_types (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name TEXT NOT NULL UNIQUE
);

-- Relacionamento Restaurante x Tipos de Cozinha (N:N)
CREATE TABLE restaurant_cuisine_types (
  restaurant_id UUID NOT NULL REFERENCES restaurants(id) ON DELETE CASCADE,
  cuisine_type_id UUID NOT NULL REFERENCES cuisine_types(id) ON DELETE CASCADE,
  PRIMARY KEY (restaurant_id, cuisine_type_id)
);

-- Horários de Funcionamento (1:N)
CREATE TABLE restaurant_opening_hour (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  restaurant_id UUID NOT NULL REFERENCES restaurants(id) ON DELETE CASCADE,
  weekday TEXT NOT NULL CHECK (
    weekday IN ('SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO', 'DOMINGO')
  ),
  opening_time TIME NOT NULL,
  closing_time TIME NOT NULL
);
