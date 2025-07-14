package com.prologapp.test.interview.infra.services;

import com.prologapp.test.interview.domain.entities.Brand;
import com.prologapp.test.interview.domain.entities.Tire;
import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.entities.VehicleTire;
import com.prologapp.test.interview.domain.enums.BrandTypeEnum;
import com.prologapp.test.interview.domain.enums.TireStatusEnum;
import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import com.prologapp.test.interview.infra.dtos.CreateVehicleRequest;
import com.prologapp.test.interview.infra.dtos.VehicleResponse;
import com.prologapp.test.interview.infra.dtos.VehicleWithTiresResponse;
import com.prologapp.test.interview.infra.exceptions.*;
import com.prologapp.test.interview.infra.mappers.VehicleMapper;
import com.prologapp.test.interview.infra.mappers.VehicleResponseMapper;
import com.prologapp.test.interview.infra.repositories.BrandRepository;
import com.prologapp.test.interview.infra.repositories.TireRepository;
import com.prologapp.test.interview.infra.repositories.VehicleRepository;
import com.prologapp.test.interview.infra.repositories.VehicleTireRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    private final BrandRepository brandRepository;

    private final VehicleMapper vehicleMapper;

    private final VehicleResponseMapper vehicleResponseMapper;

    private final TireRepository tireRepository;

    private final VehicleTireRepository vehicleTireRepository;

    public Page<VehicleResponse> getAllVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable)
                .map(vehicleResponseMapper::toResponse);
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

    public VehicleWithTiresResponse findByIdWithTires(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Nenhum veículo encontrado"));

        return vehicleResponseMapper.toVehicleWithTiresResponse(vehicle);
    }

    public VehicleResponse findByPlate(String plate) {
        if (StringUtils.isBlank(plate)) {
            throw new PlateNotSpecifiedException("Placa não informada");
        }

        if (!Pattern.matches("^[A-Za-z0-9]{7,8}$", plate)) {
            throw new PlatePatternDontMatchException("A placa deve conter apenas letras e números, sem hífen ou espaços (ex: ABC1234)");
        }

        Vehicle vehicle = vehicleRepository.findByPlate(plate.toUpperCase())
                .orElseThrow(() -> new VehicleNotFoundException("Nenhum veículo encontrado"));

        return vehicleResponseMapper.toResponse(vehicle);
    }

    public VehicleResponse updateStatus(Long id, VehicleStatusEnum status) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Nenhum veículo encontrado"));

        vehicle.setStatus(status);

        return vehicleResponseMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Transactional
    public void delete(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Nenhum veículo encontrado"));

        List<VehicleTire> tires = vehicle.getTires();

        if (!CollectionUtils.isEmpty(tires)) {
            for (VehicleTire vehicleTire : tires) {
                Tire tire = vehicleTire.getTire();
                tire.setStatus(TireStatusEnum.AVAILABLE);

                tireRepository.save(tire);

                vehicleTireRepository.delete(vehicleTire);
            }
        }

        vehicleRepository.delete(vehicle);
    }
}
