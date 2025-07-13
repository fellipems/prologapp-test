CREATE TABLE vehicle (
    id BIGSERIAL PRIMARY KEY,
    plate VARCHAR(8) NOT NULL UNIQUE,
    brand_id BIGINT NOT NULL REFERENCES brand(id),
    mileage INTEGER NOT NULL CHECK (mileage >= 0),
    status vehicle_status_enum NOT NULL,
    tires_quantity INTEGER NOT NULL CHECK (tires_quantity >= 0 AND tires_quantity <= 20)
);