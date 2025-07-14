package com.prologapp.test.interview.infra.mappers;

import com.prologapp.test.interview.domain.entities.Tire;
import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.entities.VehicleTire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleTireMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicle", source = "vehicle")
    @Mapping(target = "tire", source = "tire")
    @Mapping(target = "positionId", source = "positionId")
    VehicleTire toEntity(Vehicle vehicle, Tire tire, Integer positionId);

}
