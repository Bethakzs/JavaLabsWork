CREATE TABLE user_roles
(
    user_id BIGINT      NOT NULL,
    role    VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);