CREATE INDEX idx_refresh_token ON users (refresh_token);

CREATE UNIQUE INDEX idx_email ON users (email);

CREATE UNIQUE INDEX idx_phone_number ON users (phone_number);

CREATE INDEX idx_user_roles ON user_roles (role);
