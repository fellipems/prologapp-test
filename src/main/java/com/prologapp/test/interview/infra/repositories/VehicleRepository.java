package com.prologapp.test.interview.infra.repositories;

import com.prologapp.test.interview.domain.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Long, Vehicle> {
}
