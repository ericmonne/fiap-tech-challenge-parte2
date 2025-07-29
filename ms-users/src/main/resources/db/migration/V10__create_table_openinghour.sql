-- Tipo enumerado para dias da semana
CREATE TYPE weekday AS ENUM ('SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO', 'DOMINGO');

-- Tabela de horário de funcionamento usando UUID
CREATE TABLE opening_hour (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    restaurant_id UUID NOT NULL,
    weekday weekday NOT NULL,
    opening_time TIME NOT NULL,
    closing_time TIME NOT NULL,
    CONSTRAINT fk_opening_hour_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);
