INSERT INTO brand (name, type) VALUES ('Volvo', 'VEHICLE');
INSERT INTO brand (name, type) VALUES ('Mercedes', 'VEHICLE');

INSERT INTO brand (name, type) VALUES ('Pirelli', 'TIRE');
INSERT INTO brand (name, type) VALUES ('Bridgestone', 'TIRE');

INSERT INTO tire (fire_number, brand_id, pressure_psi, status) VALUES ('188', 3, 100, 'AVAILABLE');
INSERT INTO tire (fire_number, brand_id, pressure_psi, status) VALUES ('178', 3, 98, 'AVAILABLE');
INSERT INTO tire (fire_number, brand_id, pressure_psi, status) VALUES ('289', 4, 102, 'AVAILABLE');
INSERT INTO tire (fire_number, brand_id, pressure_psi, status) VALUES ('18', 4, 97, 'AVAILABLE');

INSERT INTO vehicle (plate, brand_id, mileage, status, tires_quantity) VALUES ('ABC1234', 1, 90000, 'ACTIVE', 8);
INSERT INTO vehicle (plate, brand_id, mileage, status, tires_quantity) VALUES ('XYZ5678', 2, 30000, 'ACTIVE', 4);

INSERT INTO vehicle_tire (vehicle_id, tire_id, position_id) VALUES (1, 1, 1);
INSERT INTO vehicle_tire (vehicle_id, tire_id, position_id) VALUES (1, 2, 2);