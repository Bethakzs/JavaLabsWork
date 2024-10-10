INSERT INTO apartments (name, price, type)
VALUES ('House 1', 2000, 'ECONOMIC'),
       ('House 2', 2500, 'STANDARD'),
       ('House 3', 5000, 'LUXURY'),
       ('House 4', 7000, 'LUXURY'),
       ('Room 1', 800, 'APARTMENT'),
       ('Room 2', 1000, 'APARTMENT'),
       ('Room 3', 1500, 'APARTMENT'),
       ('Hotel 1', 5000, 'LUXURY');

INSERT INTO amenities (id, amenity_type, additional_space, category)
VALUES (1, 'WI_FI', 0, NULL),
       (2, 'KITCHEN', 0, NULL),
       (3, 'BATHROOM', 0, NULL),
       (4, 'SECOND_BATHROOM', 0, NULL),
       (5, 'SPA', 0, NULL),
       (6, 'FOOTBALL_STADIUM', 0, NULL),
       (7, 'SWIMMING_POOL', 0, NULL),
       (8, 'SOFA', 1, 'ADULT'),
       (9, 'DOUBLE_SOFA', 2, 'ADULT'),
       (10, 'CHILD_BED', 1, 'CHILD'),
       (11, 'DOG_HOUSE', 1, 'ANIMAL');

INSERT INTO houses (id, max_space, children_max_space, animal_max_space)
VALUES (1, 3, 0, 0),
       (2, 6, 0, 0),
       (3, 10, 1, 0),
       (4, 3, 0, 1);

INSERT INTO hotels (id)
VALUES (8);

INSERT INTO rooms (id, max_space, children_max_space, animal_max_space, hotel_id)
VALUES (5, 3, 1, 0, 8), -- Room 1 associated with Hotel 1
       (6, 4, 0, 0, 8), -- Room 2 associated with Hotel 1
       (7, 3, 0, 0, 8); -- Room 3 associated with Hotel 1

INSERT INTO hotels_rooms (hotel_id, rooms_id)
VALUES (8, 5), -- Hotel 1 with Room 1
       (8, 6), -- Hotel 1 with Room 2
       (8, 7);
-- Hotel 1 with Room 3

-- Insert into apartment_amenities
-- House 1 amenities
INSERT INTO apartment_amenities (apartment_id, amenity_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 5),
       (1, 9);

-- House 2 amenities
INSERT INTO apartment_amenities (apartment_id, amenity_id)
VALUES (2, 1),
       (2, 2),
       (2, 3),
       (2, 5),
       (2, 8);

-- House 3 amenities
INSERT INTO apartment_amenities (apartment_id, amenity_id)
VALUES (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (3, 10);

-- House 4 amenities
INSERT INTO apartment_amenities (apartment_id, amenity_id)
VALUES (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6),
       (4, 7),
       (4, 11);

-- Room 1 amenities
INSERT INTO apartment_amenities (apartment_id, amenity_id)
VALUES (5, 1),
       (5, 2),
       (5, 10);

-- Room 2 amenities
INSERT INTO apartment_amenities (apartment_id, amenity_id)
VALUES (6, 1),
       (6, 4),
       (6, 9);

-- Hotel 1 amenities
INSERT INTO apartment_amenities (apartment_id, amenity_id)
VALUES (8, 1),
       (8, 2),
       (8, 3),
       (8, 11);
