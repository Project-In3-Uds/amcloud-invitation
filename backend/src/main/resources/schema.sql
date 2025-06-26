-- Drop existing tables
DROP TABLE IF EXISTS invitation;
-- Create tables
CREATE TABLE IF NOT EXISTS invitation (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255),
    token VARCHAR(255),
    expiration_date TIMESTAMP,
    accepted BOOLEAN NOT NULL DEFAULT false
);
