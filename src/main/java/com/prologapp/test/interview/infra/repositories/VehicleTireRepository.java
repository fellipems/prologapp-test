package com.prologapp.test.interview.infra.repositories;

import com.prologapp.test.interview.domain.entities.VehicleTire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleTireRepository extends JpaRepository<VehicleTire, Long> {

    boolean existsByTireId(Long tireId);

    boolean existsByVehicleIdAndPositionId(Long vehicleId, Integer positionId);

    Optional<VehicleTire> findByVehicleIdAndTireId(Long vehicleId, Long tireId);

}
