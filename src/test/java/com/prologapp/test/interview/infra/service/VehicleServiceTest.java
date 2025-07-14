package com.prologapp.test.interview.infra.service;

import com.prologapp.test.interview.domain.entities.Brand;
import com.prologapp.test.interview.domain.entities.Tire;
import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.entities.VehicleTire;
import com.prologapp.test.interview.domain.enums.BrandTypeEnum;
import com.prologapp.test.interview.domain.enums.TireStatusEnum;
import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import com.prologapp.test.interview.infra.dtos.CreateVehicleRequest;
import com.prologapp.test.interview.infra.dtos.TireResponse;
import com.prologapp.test.interview.infra.dtos.VehicleResponse;
import com.prologapp.test.interview.infra.dtos.VehicleWithTiresResponse;
import com.prologapp.test.interview.infra.exceptions.*;
import com.prologapp.test.interview.infra.mappers.VehicleMapper;
import com.prologapp.test.interview.infra.mappers.VehicleResponseMapper;
import com.prologapp.test.interview.infra.repositories.BrandRepository;
import com.prologapp.test.interview.infra.repositories.TireRepository;
import com.prologapp.test.interview.infra.repositories.VehicleRepository;
import com.prologapp.test.interview.infra.repositories.VehicleTireRepository;
import com.prologapp.test.interview.infra.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;

    @Mock
    BrandRepository brandRepository;

    @Mock
    VehicleMapper vehicleMapper;

    @Mock
    VehicleResponseMapper vehicleResponseMapper;

    @Mock
    TireRepository tireRepository;

    @Mock
    VehicleTireRepository vehicleTireRepository;

    @InjectMocks
    VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar veículo quando todos os dados forem válidos")
    void shouldCreateVehicleWhenRequestIsValid() {
        CreateVehicleRequest request = new CreateVehicleRequest(
                "ABC1234", 1L, 1000, VehicleStatusEnum.ACTIVE, 4
        );
        Brand brand = new Brand(1L, "VOLVO", BrandTypeEnum.VEHICLE);
        Vehicle vehicleEntity = mock(Vehicle.class);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(vehicleRepository.findByPlate("ABC1234")).thenReturn(Optional.empty());
        when(vehicleMapper.toEntity(request, brand)).thenReturn(vehicleEntity);
        when(vehicleRepository.save(vehicleEntity)).thenReturn(vehicleEntity);

        Vehicle vehicleResult = vehicleService.create(request);

        assertNotNull(vehicleResult);
        verify(brandRepository).findById(1L);
        verify(vehicleRepository).findByPlate("ABC1234");
        verify(vehicleMapper).toEntity(request, brand);
        verify(vehicleRepository).save(vehicleEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não for informado marca do veículo")
    void shouldThrowWhenBrandIdIsNull() {
        CreateVehicleRequest request = new CreateVehicleRequest(
                "ABC1234", null, 1000, VehicleStatusEnum.ACTIVE, 4
        );

        assertThrows(BrandNotSpecifiedException.class, () -> vehicleService.create(request));
        verifyNoInteractions(brandRepository, vehicleRepository, vehicleMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar marca com ID informado")
    void shouldThrowWhenBrandNotFound() {
        CreateVehicleRequest request = new CreateVehicleRequest(
                "ABC1234", 2L, 1000, VehicleStatusEnum.ACTIVE, 4
        );

        when(brandRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> vehicleService.create(request));
        verify(brandRepository).findById(2L);
        verifyNoMoreInteractions(vehicleRepository, vehicleMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção caso seja enviado uma marca de pneu ao invés de uma marca de veículo")
    void shouldThrowWhenBrandIsNotVehicleType() {
        CreateVehicleRequest request = new CreateVehicleRequest(
                "ABC1234", 3L, 1000, VehicleStatusEnum.ACTIVE, 4
        );

        Brand brand = new Brand(3L, "PIRELLI", BrandTypeEnum.TIRE);

        when(brandRepository.findById(3L)).thenReturn(Optional.of(brand));

        assertThrows(VehicleCreateTypeIsNotVehicleException.class, () -> vehicleService.create(request));
        verify(brandRepository).findById(3L);
    }

    @Test
    @DisplayName("Deve lançar exceção caso tente cadastrar um veículo em que já tenha uma placa cadastrada na base de dados")
    void shouldThrowWhenPlateAlreadyExists() {
        CreateVehicleRequest request = new CreateVehicleRequest(
                "ABC1234", 1L, 1000, VehicleStatusEnum.ACTIVE, 4
        );

        Brand brand = new Brand(1L, "VOLVO", BrandTypeEnum.VEHICLE);

        Vehicle vehicleEntity = mock(Vehicle.class);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(vehicleRepository.findByPlate("ABC1234")).thenReturn(Optional.of(vehicleEntity));

        assertThrows(VehicleAlreadyExistsWithGivenPlateException.class, () -> vehicleService.create(request));
        verify(brandRepository).findById(1L);
        verify(vehicleRepository).findByPlate("ABC1234");
    }

    @Test
    @DisplayName("Deve retornar página de veículos cadastrados sem os pneus")
    void shouldReturnAllVehiclesPageable() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Vehicle> vehicles = Arrays.asList(mock(Vehicle.class), mock(Vehicle.class));
        Page<Vehicle> pageVehicles = new PageImpl<>(vehicles, pageable, vehicles.size());

        List<VehicleResponse> vehiclesResponseList = Arrays.asList(
                mock(VehicleResponse.class), mock(VehicleResponse.class)
        );

        when(vehicleRepository.findAll(pageable)).thenReturn(pageVehicles);
        when(vehicleResponseMapper.toResponse(any(Vehicle.class)))
                .thenReturn(vehiclesResponseList.get(0), vehiclesResponseList.get(1));

        Page<VehicleResponse> result = vehicleService.getAllVehicles(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(vehicleRepository).findAll(pageable);
        verify(vehicleResponseMapper, times(2)).toResponse(any(Vehicle.class));
    }

    @Test
    @DisplayName("Deve retornar veículo com id válido e com os pneus vinculados")
    void shouldReturnVehicleWithTiresWhenIdExists() {
        Vehicle vehicle = mock(Vehicle.class);

        TireResponse tireResponse = new TireResponse(
                1L, "FIRE123", "PIRELLI", 90, TireStatusEnum.AVAILABLE, 1
        );

        List<TireResponse> tires = List.of(tireResponse);

        VehicleWithTiresResponse vehicleWithtires = new VehicleWithTiresResponse(
                1L, "ABC1234", "VOLVO", 10000, VehicleStatusEnum.ACTIVE, 6, tires
        );

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleResponseMapper.toVehicleWithTiresResponse(vehicle)).thenReturn(vehicleWithtires);

        VehicleWithTiresResponse vehicleWithTiresResult = vehicleService.findByIdWithTires(1L);

        assertEquals(vehicleWithtires, vehicleWithTiresResult);
        assertNotNull(vehicleWithTiresResult.tires(), "Lista de pneus não nula");
        assertFalse(vehicleWithTiresResult.tires().isEmpty(), "Lista de pneus não vazia");
        assertEquals(1, vehicleWithTiresResult.tires().size());
        assertEquals("FIRE123", vehicleWithTiresResult.tires().getFirst().fireNumber());

        verify(vehicleRepository).findById(1L);
        verify(vehicleResponseMapper).toVehicleWithTiresResponse(vehicle);
    }

    @Test
    @DisplayName("Deve lançar exceção caso veículo não exista")
    void shouldThrowWhenVehicleNotFoundById() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.findByIdWithTires(99L));
        verify(vehicleRepository).findById(99L);
    }

    @Test
    @DisplayName("Deve retornar o veículo quando a placa for válida e existir na base mesmo se for lowerCase")
    void shouldReturnVehicleWhenPlateExists() {
        String plate = "aaa1234";
        Vehicle vehicle = mock(Vehicle.class);
        VehicleResponse response = mock(VehicleResponse.class);

        when(vehicleRepository.findByPlate("AAA1234")).thenReturn(Optional.of(vehicle));
        when(vehicleResponseMapper.toResponse(vehicle)).thenReturn(response);

        VehicleResponse result = vehicleService.findByPlate(plate);

        assertNotNull(result);
        verify(vehicleRepository).findByPlate("AAA1234");
        verify(vehicleResponseMapper).toResponse(vehicle);
    }

    @Test
    @DisplayName("Deve lançar PlateNotSpecifiedException se a placa for vazia ou nula")
    void shouldThrowPlateNotSpecifiedExceptionWhenPlateIsNullOrEmpty() {
        assertThrows(PlateNotSpecifiedException.class, () -> vehicleService.findByPlate(null));
        assertThrows(PlateNotSpecifiedException.class, () -> vehicleService.findByPlate(""));
        assertThrows(PlateNotSpecifiedException.class, () -> vehicleService.findByPlate(" "));
    }

    @Test
    @DisplayName("Deve lançar PlatePatternDontMatchException se a placa não estiver no padrão")
    void shouldThrowPlatePatternDontMatchExceptionWhenPlateIsInvalid() {
        assertThrows(PlatePatternDontMatchException.class, () -> vehicleService.findByPlate("ABC-1234"));
        assertThrows(PlatePatternDontMatchException.class, () -> vehicleService.findByPlate("123 4567"));
        assertThrows(PlatePatternDontMatchException.class, () -> vehicleService.findByPlate("12@#123"));
    }

    @Test
    @DisplayName("Deve lançar VehicleNotFoundException quando o veículo não existe para a placa informada")
    void shouldThrowVehicleNotFoundExceptionWhenPlateNotFound() {
        String plate = "abc1234";

        when(vehicleRepository.findByPlate("ABC1234")).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class, () -> vehicleService.findByPlate(plate));
        verify(vehicleRepository).findByPlate("ABC1234");
    }

    @Test
    @DisplayName("Deve atualizar o status do veículo quando existir o veículo")
    void shouldUpdateVehicleStatusWhenVehicleExists() {
        Long id = 1L;
        VehicleStatusEnum status = VehicleStatusEnum.INACTIVE;
        Vehicle vehicle = new Vehicle();
        vehicle.setStatus(VehicleStatusEnum.ACTIVE);

        Vehicle savedVehicle = new Vehicle();
        savedVehicle.setStatus(status);

        VehicleResponse response = mock(VehicleResponse.class);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(savedVehicle);
        when(vehicleResponseMapper.toResponse(savedVehicle)).thenReturn(response);

        VehicleResponse result = vehicleService.updateStatus(id, status);

        assertEquals(status, vehicle.getStatus());
        assertEquals(response, result);
        verify(vehicleRepository).findById(id);
        verify(vehicleRepository).save(vehicle);
        verify(vehicleResponseMapper).toResponse(savedVehicle);
    }

    @Test
    @DisplayName("Deve lançar VehicleNotFoundException quando o veículo não existir")
    void shouldThrowWhenVehicleNotFound() {
        Long id = 99L;
        VehicleStatusEnum status = VehicleStatusEnum.ACTIVE;

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(VehicleNotFoundException.class,
                () -> vehicleService.updateStatus(id, status));

        verify(vehicleRepository).findById(id);
        verify(vehicleRepository, never()).save(any());
        verify(vehicleResponseMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Deve remover o veículo, desvincular os pneus e atualizar status dos pneus para AVAILABLE")
    void shouldDeleteVehicleAndUnlinkTires() {
        Long vehicleId = 1L;
        Vehicle vehicle = mock(Vehicle.class);
        VehicleTire vt1 = mock(VehicleTire.class);
        VehicleTire vt2 = mock(VehicleTire.class);
        Tire tire1 = mock(Tire.class);
        Tire tire2 = mock(Tire.class);

        List<VehicleTire> tires = Arrays.asList(vt1, vt2);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(vehicle.getTires()).thenReturn(tires);
        when(vt1.getTire()).thenReturn(tire1);
        when(vt2.getTire()).thenReturn(tire2);

        vehicleService.delete(vehicleId);

        verify(tire1).setStatus(TireStatusEnum.AVAILABLE);
        verify(tire2).setStatus(TireStatusEnum.AVAILABLE);
        verify(tireRepository).save(tire1);
        verify(tireRepository).save(tire2);
        verify(vehicleTireRepository).delete(vt1);
        verify(vehicleTireRepository).delete(vt2);
        verify(vehicleRepository).delete(vehicle);
    }

    @Test
    @DisplayName("Deve remover o veículo mesmo que não haja pneus vinculados")
    void shouldDeleteVehicleWithNoTires() {
        Long vehicleId = 2L;
        Vehicle vehicle = mock(Vehicle.class);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(vehicle.getTires()).thenReturn(Collections.emptyList());

        vehicleService.delete(vehicleId);

        verify(vehicleRepository).delete(vehicle);

        verifyNoInteractions(tireRepository, vehicleTireRepository);
    }

    @Test
    @DisplayName("Deve lançar VehicleNotFoundException se o veículo não existir")
    void shouldThrowExceptionIfVehicleNotFound() {
        Long vehicleId = 3L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        VehicleNotFoundException ex = assertThrows(
                VehicleNotFoundException.class,
                () -> vehicleService.delete(vehicleId)
        );

        assertEquals("Nenhum veículo encontrado", ex.getMessage());
        verify(vehicleRepository).findById(vehicleId);

        verifyNoMoreInteractions(vehicleRepository, tireRepository, vehicleTireRepository);
    }

}
