package com.prologapp.test.interview.infra.mappers;

import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.entities.VehicleTire;
import com.prologapp.test.interview.infra.dtos.TireResponse;
import com.prologapp.test.interview.infra.dtos.VehicleResponse;
import com.prologapp.test.interview.infra.dtos.VehicleWithTiresResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleResponseMapper {

    @Mapping(target = "brand", source = "tire.brand.name")
    @Mapping(target = "id", source = "tire.id")
    @Mapping(target = "fireNumber", source = "tire.fireNumber")
    @Mapping(target = "pressurePsi", source = "tire.pressurePsi")
    @Mapping(target = "status", source = "tire.status")
    @Mapping(target = "positionId", source = "positionId")
    TireResponse toTireResponse(VehicleTire vehicleTire);

    @Mapping(target = "brand", source = "brand.name")
    VehicleResponse toResponse(Vehicle vehicle);

    List<VehicleResponse> toResponseList(List<Vehicle> vehicles);

    @Mapping(target = "brand", source = "brand.name")
    @Mapping(target = "tires", expression = "java(mapVehicleTires(vehicle.getTires()))")
    VehicleWithTiresResponse toVehicleWithTiresResponse(Vehicle vehicle);

    default List<TireResponse> mapVehicleTires(List<VehicleTire> vehicleTires) {
        return vehicleTires.stream()
                .map(this::toTireResponse)
                .toList();
    }

}
