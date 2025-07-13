package com.prologapp.test.interview.infra.repositories;

import com.prologapp.test.interview.domain.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
