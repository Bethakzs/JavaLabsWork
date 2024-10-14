CREATE TABLE amenities
(
    id               BIGSERIAL PRIMARY KEY,
    amenity_type     VARCHAR(50) NOT NULL,
    additional_space INT         NOT NULL DEFAULT 0,
    category         VARCHAR(20),
    CONSTRAINT chk_category CHECK (category IN ('ADULT', 'CHILD', 'BABY', 'ANIMAL'))
);

