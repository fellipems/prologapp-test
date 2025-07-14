package com.prologapp.test.interview.infra.service;

import com.prologapp.test.interview.domain.entities.Brand;
import com.prologapp.test.interview.domain.entities.Tire;
import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.entities.VehicleTire;
import com.prologapp.test.interview.domain.enums.BrandTypeEnum;
import com.prologapp.test.interview.domain.enums.TireStatusEnum;
import com.prologapp.test.interview.infra.dtos.CreateTireRequest;
import com.prologapp.test.interview.infra.dtos.LinkTireRequest;
import com.prologapp.test.interview.infra.dtos.TireResponse;
import com.prologapp.test.interview.infra.dtos.UnlinkTireRequest;
import com.prologapp.test.interview.infra.exceptions.*;
import com.prologapp.test.interview.infra.mappers.TireMapper;
import com.prologapp.test.interview.infra.mappers.VehicleTireMapper;
import com.prologapp.test.interview.infra.repositories.BrandRepository;
import com.prologapp.test.interview.infra.repositories.TireRepository;
import com.prologapp.test.interview.infra.repositories.VehicleRepository;
import com.prologapp.test.interview.infra.repositories.VehicleTireRepository;
import com.prologapp.test.interview.infra.services.TireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TireServiceTest {

    @Mock
    TireRepository tireRepository;

    @Mock
    VehicleRepository vehicleRepository;

    @Mock
    VehicleTireRepository vehicleTireRepository;

    @Mock
    BrandRepository brandRepository;

    @Mock
    TireMapper tireMapper;

    @Mock
    VehicleTireMapper vehicleTireMapper;

    @InjectMocks
    TireService tireService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar pneu com sucesso quando todos os dados forem válidos")
    void shouldCreateTireWhenValidRequest() {
        CreateTireRequest request = new CreateTireRequest("FIRE123", 2L, 90, TireStatusEnum.AVAILABLE);
        Brand brand = new Brand(2L, "PIRELLI", BrandTypeEnum.TIRE);
        Tire tire = new Tire();
        Tire saved = new Tire();
        TireResponse response = mock(TireResponse.class);

        when(brandRepository.findById(2L)).thenReturn(Optional.of(brand));
        when(tireRepository.findByFireNumber("FIRE123")).thenReturn(Optional.empty());
        when(tireMapper.toEntity(request, brand)).thenReturn(tire);
        when(tireRepository.save(tire)).thenReturn(saved);
        when(tireMapper.toResponse(saved, null)).thenReturn(response);

        TireResponse tireResult = tireService.create(request);

        assertEquals(response, tireResult);
        verify(brandRepository).findById(2L);
        verify(tireRepository).findByFireNumber("FIRE123");
        verify(tireMapper).toEntity(request, brand);
        verify(tireRepository).save(tire);
        verify(tireMapper).toResponse(saved, null);
    }

    @Test
    @DisplayName("Deve lançar exceção se brandId não for informado ao criar pneu")
    void shouldThrowWhenBrandIdIsNullOnCreate() {
        CreateTireRequest request = new CreateTireRequest("FIRE123", null, 90, TireStatusEnum.AVAILABLE);

        assertThrows(BrandNotSpecifiedException.class, () -> tireService.create(request));
        verifyNoInteractions(brandRepository, tireRepository, tireMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção se marca não for encontrada ao criar pneu")
    void shouldThrowWhenBrandNotFoundOnCreate() {
        CreateTireRequest request = new CreateTireRequest("FIRE123", 2L, 90, TireStatusEnum.AVAILABLE);
        when(brandRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> tireService.create(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se marca não for do tipo TIRE ao criar pneu")
    void shouldThrowWhenBrandIsNotTireTypeOnCreate() {
        CreateTireRequest request = new CreateTireRequest("FIRE123", 2L, 90, TireStatusEnum.AVAILABLE);
        Brand brand = new Brand(2L, "VOLVO", BrandTypeEnum.VEHICLE);
        when(brandRepository.findById(2L)).thenReturn(Optional.of(brand));

        assertThrows(TireCreateTypeIsNotTireException.class, () -> tireService.create(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se já existir pneu com mesmo número de fogo ao criar pneu")
    void shouldThrowWhenTireWithSameFireNumberExistsOnCreate() {
        CreateTireRequest request = new CreateTireRequest("FIRE123", 2L, 90, TireStatusEnum.AVAILABLE);
        Brand brand = new Brand(2L, "PIRELLI", BrandTypeEnum.TIRE);
        Tire existsTire = new Tire();

        when(brandRepository.findById(2L)).thenReturn(Optional.of(brand));
        when(tireRepository.findByFireNumber("FIRE123")).thenReturn(Optional.of(existsTire));

        assertThrows(TireAlreadyExistsWithGivenFireNumberException.class, () -> tireService.create(request));
    }

    // --- TESTES LINK ---
    @Test
    @DisplayName("Deve vincular pneu ao veículo com sucesso quando todos os dados forem válidos")
    void shouldLinkTireToVehicleWhenValid() {
        LinkTireRequest request = new LinkTireRequest(1L, 5L, 3);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setTiresQuantity(6);

        Tire tire = new Tire();
        tire.setId(5L);
        tire.setStatus(TireStatusEnum.AVAILABLE);

        VehicleTire vehicleTire = new VehicleTire();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(5L)).thenReturn(Optional.of(tire));
        when(vehicleTireRepository.existsByTireId(5L)).thenReturn(false);
        when(vehicleTireRepository.existsByVehicleIdAndPositionId(1L, 3)).thenReturn(false);
        when(vehicleTireMapper.toEntity(vehicle, tire, 3)).thenReturn(vehicleTire);

        tireService.linkTireToVehicle(request);

        verify(vehicleTireRepository).save(vehicleTire);
        verify(tireRepository).save(tire);
        assertEquals(TireStatusEnum.IN_USE, tire.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção se veículo não for encontrado ao vincular pneu")
    void shouldThrowWhenVehicleNotFoundOnLink() {
        LinkTireRequest request = new LinkTireRequest(1L, 5L, 3);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> tireService.linkTireToVehicle(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se pneu não for encontrado ao vincular pneu ao veículo")
    void shouldThrowWhenTireNotFoundOnLink() {
        LinkTireRequest request = new LinkTireRequest(1L, 5L, 3);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(TireNotFoundException.class, () -> tireService.linkTireToVehicle(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se pneu não estiver disponível ao vincular pneu ao veículo")
    void shouldThrowWhenTireStatusNotAvailableOnLink() {
        LinkTireRequest request = new LinkTireRequest(1L, 5L, 3);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setTiresQuantity(4);

        Tire tire = new Tire();
        tire.setId(5L);
        tire.setStatus(TireStatusEnum.IN_USE);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(5L)).thenReturn(Optional.of(tire));

        assertThrows(TireNotAvailableException.class, () -> tireService.linkTireToVehicle(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se pneu já estiver vinculado a outro veículo")
    void shouldThrowWhenTireAlreadyLinkedOnLink() {
        LinkTireRequest request = new LinkTireRequest(1L, 5L, 3);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setTiresQuantity(4);

        Tire tire = new Tire();
        tire.setId(5L);
        tire.setStatus(TireStatusEnum.AVAILABLE);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(5L)).thenReturn(Optional.of(tire));
        when(vehicleTireRepository.existsByTireId(5L)).thenReturn(true);

        assertThrows(TireAlreadyLinkedException.class, () -> tireService.linkTireToVehicle(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se já existir pneu na posição informada do veículo")
    void shouldThrowWhenVehiclePositionAlreadyOccupiedOnLink() {
        LinkTireRequest request = new LinkTireRequest(1L, 5L, 3);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setTiresQuantity(4);

        Tire tire = new Tire();
        tire.setId(5L);
        tire.setStatus(TireStatusEnum.AVAILABLE);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(5L)).thenReturn(Optional.of(tire));
        when(vehicleTireRepository.existsByTireId(5L)).thenReturn(false);
        when(vehicleTireRepository.existsByVehicleIdAndPositionId(1L, 3)).thenReturn(true);

        assertThrows(VehiclePositionAlreadyOccupiedException.class, () -> tireService.linkTireToVehicle(request));
    }

    @Test
    @DisplayName("Deve lançar exceção se a posição informada exceder o limite de pneus do veículo")
    void shouldThrowWhenPositionExceedsVehicleLimitOnLink() {
        LinkTireRequest request = new LinkTireRequest(1L, 5L, 10);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setTiresQuantity(6);

        Tire tire = new Tire();
        tire.setId(5L);
        tire.setStatus(TireStatusEnum.AVAILABLE);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(tireRepository.findById(5L)).thenReturn(Optional.of(tire));
        when(vehicleTireRepository.existsByTireId(5L)).thenReturn(false);
        when(vehicleTireRepository.existsByVehicleIdAndPositionId(1L, 10)).thenReturn(false);

        assertThrows(VehicleTireLimitExceededException.class, () -> tireService.linkTireToVehicle(request));
    }

    @Test
    @DisplayName("Deve desvincular pneu do veículo com sucesso quando vínculo existir")
    void shouldUnlinkTireFromVehicleWhenLinked() {
        UnlinkTireRequest request = new UnlinkTireRequest(1L, 3L);

        VehicleTire vehicleTire = mock(VehicleTire.class);

        Tire tire = new Tire();
        tire.setStatus(TireStatusEnum.IN_USE);

        when(vehicleTireRepository.findByVehicleIdAndTireId(1L, 3L)).thenReturn(Optional.of(vehicleTire));
        when(vehicleTire.getTire()).thenReturn(tire);

        tireService.unlinkTireFromVehicle(request);

        verify(vehicleTireRepository).delete(vehicleTire);
        verify(tireRepository).save(tire);
        assertEquals(TireStatusEnum.AVAILABLE, tire.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção se não existir vínculo entre pneu e veículo ao desvincular")
    void shouldThrowWhenTireNotLinkedOnUnlink() {
        UnlinkTireRequest request = new UnlinkTireRequest(1L, 3L);

        when(vehicleTireRepository.findByVehicleIdAndTireId(1L, 3L)).thenReturn(Optional.empty());

        assertThrows(TireNotLinkedException.class, () -> tireService.unlinkTireFromVehicle(request));
    }

}
