CREATE TABLE apartments
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255)   NOT NULL,
    price NUMERIC(38, 2) NOT NULL,
    type  VARCHAR(50)    NOT NULL
);

CREATE TABLE hotels
(
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES apartments (id) ON DELETE CASCADE
);

CREATE TABLE houses
(
    id                 BIGINT PRIMARY KEY,
    max_space          INT NOT NULL,
    children_max_space INT NOT NULL DEFAULT 0,
    animal_max_space   INT NOT NULL DEFAULT 0,
    FOREIGN KEY (id) REFERENCES apartments (id) ON DELETE CASCADE
);

CREATE TABLE rooms
(
    id                 BIGINT PRIMARY KEY,
    max_space          INT NOT NULL,
    children_max_space INT NOT NULL DEFAULT 0,
    animal_max_space   INT NOT NULL DEFAULT 0,
    hotel_id           BIGINT,
    FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE SET NULL,
    FOREIGN KEY (id) REFERENCES apartments (id) ON DELETE CASCADE
);

CREATE TABLE hotels_rooms
(
    hotel_id BIGINT NOT NULL,
    rooms_id BIGINT NOT NULL,
    PRIMARY KEY (hotel_id, rooms_id),
    FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE,
    FOREIGN KEY (rooms_id) REFERENCES rooms (id) ON DELETE CASCADE
);

CREATE TABLE apartment_amenities
(
    apartment_id BIGINT NOT NULL,
    amenity_id   BIGINT NOT NULL,
    PRIMARY KEY (apartment_id, amenity_id),
    FOREIGN KEY (apartment_id) REFERENCES apartments (id) ON DELETE CASCADE,
    FOREIGN KEY (amenity_id) REFERENCES amenities (id)
);
