CREATE TABLE brand (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type brand_type_enum NOT NULL
);