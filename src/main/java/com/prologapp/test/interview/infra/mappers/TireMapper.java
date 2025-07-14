package com.prologapp.test.interview.infra.mappers;

import com.prologapp.test.interview.domain.entities.Brand;
import com.prologapp.test.interview.domain.entities.Tire;
import com.prologapp.test.interview.infra.dtos.CreateTireRequest;
import com.prologapp.test.interview.infra.dtos.TireResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TireMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brand", source = "brand")
    Tire toEntity(CreateTireRequest request, Brand brand);

    @Mapping(target = "brand", source = "tire.brand.name")
    @Mapping(target = "positionId", source = "positionId")
    TireResponse toResponse(Tire tire, Integer positionId);

}
