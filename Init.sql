CREATE TABLE IF NOT EXISTS notice (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    type VARCHAR(50),
    processed BOOLEAN
);
