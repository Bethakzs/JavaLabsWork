CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    first_name    VARCHAR(255)   NOT NULL,
    last_name     VARCHAR(255)   NOT NULL,
    email         VARCHAR(255)   NOT NULL UNIQUE,
    password      VARCHAR(255)   NOT NULL,
    phone_number  VARCHAR(255)   NOT NULL UNIQUE,
    balance       DECIMAL(19, 4) NOT NULL,
    refresh_token VARCHAR(255)
);