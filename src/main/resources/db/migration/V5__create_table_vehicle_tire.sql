CREATE TABLE vehicle_tire (
    id BIGSERIAL PRIMARY KEY,
    vehicle_id BIGINT NOT NULL REFERENCES vehicle(id) ON DELETE CASCADE,
    tire_id BIGINT NOT NULL REFERENCES tire(id),
    position_id INTEGER NOT NULL CHECK (position_id >= 1),
    CONSTRAINT unique_vehicle_position UNIQUE(vehicle_id, position_id),
    CONSTRAINT unique_tire UNIQUE(tire_id)
);