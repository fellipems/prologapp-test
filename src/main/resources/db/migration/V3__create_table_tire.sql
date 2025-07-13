CREATE TABLE tire (
    id BIGSERIAL PRIMARY KEY,
    fire_number VARCHAR(50) NOT NULL UNIQUE,
    brand_id BIGINT NOT NULL REFERENCES brand(id),
    pressure_psi INTEGER NOT NULL CHECK (pressure_psi >= 0 AND pressure_psi <= 150),
    status tire_status_enum NOT NULL
);