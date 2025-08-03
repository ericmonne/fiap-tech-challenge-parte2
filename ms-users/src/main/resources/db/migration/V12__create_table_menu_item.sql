CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE menu_items(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price NUMERIC(12, 2) NOT NULL CHECK (price >= 0.01),
    available_only_on_site BOOLEAN NOT NULL,
    image_path VARCHAR(255) NOT NULL CHECK (image_path ~* '\\.(jpg|jpeg|png|gif)$'),
    restaurant_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT fk_restaurant
        FOREIGN KEY (restaurant_id)
        REFERENCES restaurants(id)
        ON DELETE CASCADE
);
