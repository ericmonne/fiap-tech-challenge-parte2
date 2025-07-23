CREATE TABLE user_type (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        description TEXT,
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);