package com.prologapp.test.interview.infra.controllers;

import com.prologapp.test.interview.infra.dtos.*;
import com.prologapp.test.interview.infra.exceptions.ApiError;
import com.prologapp.test.interview.infra.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Operações relacionadas a veículos")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(
            summary = "Cadastrar novo veículo",
            description = "Registra um novo veículo no sistema. Marca deve ser do tipo VEHICLE e placa deve ser única."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Veículo cadastrado com sucesso",
            content = @Content(schema = @Schema(implementation = CreateVehicleResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos ou regra de negócio violada",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Marca não encontrada",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @PostMapping
    public ResponseEntity<CreateVehicleResponse> create(@RequestBody CreateVehicleRequest newVehicle) {
        return ResponseEntity.ok(
                CreateVehicleResponse
                        .fromEntity(vehicleService.create(newVehicle))
        );
    }

    @Operation(
            summary = "Listar veículos paginados",
            description = "Retorna uma página de veículos cadastrados (sem exibir os pneus). Use os parâmetros de paginação: page, size e sort."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Página de veículos retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PageResponse.class))
    )
    @GetMapping
    public ResponseEntity<PageResponse<VehicleResponse>> getAllVehicles(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(PageResponse.from(vehicleService.getAllVehicles(pageable)));
    }

    @Operation(
            summary = "Buscar veículo por ID (com pneus)",
            description = "Retorna as informações de um veículo específico, incluindo todos os pneus aplicados e suas posições."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Veículo encontrado",
            content = @Content(schema = @Schema(implementation = VehicleWithTiresResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Nenhum veículo encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @GetMapping("/{id}")
    public ResponseEntity<VehicleWithTiresResponse> getVehicleByIdWithTires(@PathVariable Long id) {

        return ResponseEntity.ok(vehicleService.findByIdWithTires(id));
    }

    @Operation(
            summary = "Busca veículo pela placa (sem pneus)",
            description = "Retorna as informações de um veículo específico pela sua placa"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Veículo encontrado",
            content = @Content(schema = @Schema(implementation = VehicleResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Nenhum veículo encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @GetMapping("/plate/{plate}")
    public ResponseEntity<VehicleResponse> getVehicleByIdWithTires(@PathVariable String plate) {

        return ResponseEntity.ok(vehicleService.findByPlate(plate));

    }

}
