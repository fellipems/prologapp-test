package com.prologapp.test.interview.infra.controllers;

import com.prologapp.test.interview.infra.dtos.CreateTireRequest;
import com.prologapp.test.interview.infra.dtos.LinkTireRequest;
import com.prologapp.test.interview.infra.dtos.TireResponse;
import com.prologapp.test.interview.infra.dtos.UnlinkTireRequest;
import com.prologapp.test.interview.infra.exceptions.ApiError;
import com.prologapp.test.interview.infra.services.TireService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tires")
@RequiredArgsConstructor
@Tag(name = "Pneus", description = "Operações relacionadas a pneus")
public class TireController {

    private final TireService tireService;

    @Operation(
            summary = "Cadastrar novo pneu",
            description = "Cadastra um novo pneu no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Pneu cadastrado", content = @Content(schema = @Schema(implementation = TireResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "Marca não encontrada", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping
    public ResponseEntity<TireResponse> create(@RequestBody CreateTireRequest request) {
        return ResponseEntity.ok(tireService.create(request));
    }

    @Operation(summary = "Vincular pneu a um veículo")
    @ApiResponse(responseCode = "200", description = "Pneu vinculado")
    @ApiResponse(responseCode = "400", description = "Regra de negócio violada", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "404", description = "Veículo ou pneu não encontrado", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping("/link")
    public ResponseEntity<Void> linkTire(@RequestBody LinkTireRequest request) {
        tireService.linkTireToVehicle(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Desvincular pneu de um veículo")
    @ApiResponse(responseCode = "200", description = "Pneu desvinculado")
    @ApiResponse(responseCode = "400", description = "Pneu não vinculado", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping("/unlink")
    public ResponseEntity<Void> unlinkTire(@RequestBody UnlinkTireRequest request) {
        tireService.unlinkTireFromVehicle(request);
        return ResponseEntity.ok().build();
    }

}
