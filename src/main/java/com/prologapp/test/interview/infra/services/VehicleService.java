package com.prologapp.test.interview.infra.services;

import com.prologapp.test.interview.domain.entities.Brand;
import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.enums.BrandTypeEnum;
import com.prologapp.test.interview.infra.dtos.CreateVehicleRequest;
import com.prologapp.test.interview.infra.exceptions.BrandNotFoundException;
import com.prologapp.test.interview.infra.exceptions.BrandNotSpecifiedException;
import com.prologapp.test.interview.infra.exceptions.VehicleAlreadyExistsWithGivenPlateException;
import com.prologapp.test.interview.infra.exceptions.VehicleCreateTypeIsNotVehicleException;
import com.prologapp.test.interview.infra.mappers.VehicleMapper;
import com.prologapp.test.interview.infra.repositories.BrandRepository;
import com.prologapp.test.interview.infra.repositories.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    private final BrandRepository brandRepository;

    private final VehicleMapper vehicleMapper;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle create(@Valid CreateVehicleRequest newVehicle) {
        if (Objects.isNull(newVehicle.brandId())) {
            throw new BrandNotSpecifiedException("Marca do veículo não informada");
        }

        Brand brand = brandRepository.findById(newVehicle.brandId())
                .orElseThrow(() -> new BrandNotFoundException("Marca não encontrada"));

        if (!brand.getType().equals(BrandTypeEnum.VEHICLE)) {
            throw new VehicleCreateTypeIsNotVehicleException("Não é possível criar um veículo com marca de pneu");
        }

        Optional<Vehicle> existsVehicle = vehicleRepository.findByPlate(newVehicle.plate());

        if (existsVehicle.isPresent()) {
            throw new VehicleAlreadyExistsWithGivenPlateException("Já existe um veiculo cadastrado com a placa informada");
        }

        Vehicle vehicle = vehicleMapper.toEntity(newVehicle, brand);

        return vehicleRepository.save(vehicle);
    }
}
