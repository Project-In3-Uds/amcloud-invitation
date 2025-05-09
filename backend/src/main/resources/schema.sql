CREATE TABLE IF NOT EXISTS invitation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    token VARCHAR(255),
    expiration_date TIMESTAMP,
    accepted BOOLEAN
);
