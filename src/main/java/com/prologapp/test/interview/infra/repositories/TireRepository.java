package com.prologapp.test.interview.infra.repositories;

import com.prologapp.test.interview.domain.entities.Tire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TireRepository extends JpaRepository<Tire, Long> {

    Optional<Tire> findByFireNumber(String fireNumber);

}
