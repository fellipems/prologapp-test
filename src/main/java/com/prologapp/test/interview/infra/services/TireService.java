package com.prologapp.test.interview.infra.services;

import com.prologapp.test.interview.domain.entities.Brand;
import com.prologapp.test.interview.domain.entities.Tire;
import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.entities.VehicleTire;
import com.prologapp.test.interview.domain.enums.BrandTypeEnum;
import com.prologapp.test.interview.domain.enums.TireStatusEnum;
import com.prologapp.test.interview.infra.dtos.*;
import com.prologapp.test.interview.infra.exceptions.*;
import com.prologapp.test.interview.infra.mappers.TireMapper;
import com.prologapp.test.interview.infra.mappers.VehicleTireMapper;
import com.prologapp.test.interview.infra.repositories.BrandRepository;
import com.prologapp.test.interview.infra.repositories.TireRepository;
import com.prologapp.test.interview.infra.repositories.VehicleRepository;
import com.prologapp.test.interview.infra.repositories.VehicleTireRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TireService {

    private final TireRepository tireRepository;

    private final VehicleRepository vehicleRepository;

    private final VehicleTireRepository vehicleTireRepository;

    private final BrandRepository brandRepository;

    private final TireMapper tireMapper;

    private final VehicleTireMapper vehicleTireMapper;

    public TireResponse create(CreateTireRequest request) {
        if (Objects.isNull(request.brandId())) {
            throw new BrandNotSpecifiedException("Marca do pneu não informado");
        }

        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new BrandNotFoundException("Marca não encontrada"));

        if (!BrandTypeEnum.TIRE.equals(brand.getType())) {
            throw new TireCreateTypeIsNotTireException("Não é possível criar um pneu com marca de veículo");
        }

        Optional<Tire> existsTire = tireRepository.findByFireNumber(request.fireNumber());

        if (existsTire.isPresent()) {
            throw new TireAlreadyExistsWithGivenFireNumberException("Já existe pneu cadastrado com número de fogo informado");
        }

        Tire tire = tireMapper.toEntity(request, brand);
        tire.setStatus(TireStatusEnum.AVAILABLE);

        Tire tireSaved = tireRepository.save(tire);

        return tireMapper.toResponse(tireSaved, null);
    }

    @Transactional
    public void linkTireToVehicle(LinkTireRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException("Veículo não encontrado"));

        Tire tire = tireRepository.findById(request.tireId())
                .orElseThrow(() -> new TireNotFoundException("Pneu não encontrado"));

        if (!TireStatusEnum.AVAILABLE.equals(tire.getStatus())) {
            throw new TireNotAvailableException("Pneu não está disponível para uso.");
        }

        if (vehicleTireRepository.existsByTireId(request.tireId())) {
            throw new TireAlreadyLinkedException("Pneu já está vinculado a um veículo");
        }

        if (vehicleTireRepository.existsByVehicleIdAndPositionId(request.vehicleId(), request.positionId())) {
            throw new VehiclePositionAlreadyOccupiedException("Já existe pneu na posição informada para o veículo");
        }

        if (request.positionId() > vehicle.getTiresQuantity()) {
            throw new VehicleTireLimitExceededException("Posição excede a quantidade de pneus do veículo");
        }

        VehicleTire vehicleTire = vehicleTireMapper.toEntity(vehicle, tire, request.positionId());
        vehicleTireRepository.save(vehicleTire);

        tire.setStatus(TireStatusEnum.IN_USE);
        tireRepository.save(tire);
    }

    @Transactional
    public void unlinkTireFromVehicle(UnlinkTireRequest request) {
        VehicleTire vehicleTire = vehicleTireRepository.findByVehicleIdAndTireId(request.vehicleId(), request.tireId())
                .orElseThrow(() -> new TireNotLinkedException("Pneu não está vinculado a esse veículo"));

        vehicleTireRepository.delete(vehicleTire);

        Tire tire = vehicleTire.getTire();
        tire.setStatus(TireStatusEnum.AVAILABLE);

        tireRepository.save(tire);
    }

    @Transactional
    public void delete(Long tireId) {
        Tire tire = tireRepository.findById(tireId)
                .orElseThrow(() -> new TireNotFoundException("Pneu não encontrado"));

        if (!TireStatusEnum.AVAILABLE.equals(tire.getStatus())) {
            throw new TireIsLinkedException("Pneu está vinculado a um veículo e não pode ser removido. Desvincule para excluir o pneu");
        }

        tireRepository.delete(tire);
    }

    public Page<TireResponse> getAllTires(Pageable pageable) {
        return tireRepository
                .findAll(pageable)
                .map(tireMapper::toResponseWithoutPosition);
    }

}
