package com.prologapp.test.interview.infra.mappers;

import com.prologapp.test.interview.domain.entities.Brand;
import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.infra.dtos.CreateVehicleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "brand")
    Vehicle toEntity(CreateVehicleRequest request, Brand brand);

}
