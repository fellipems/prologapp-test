package com.prologapp.test.interview.infra.controllers;

import com.prologapp.test.interview.infra.dtos.CreateTireRequest;
import com.prologapp.test.interview.infra.dtos.LinkTireRequest;
import com.prologapp.test.interview.infra.dtos.TireResponse;
import com.prologapp.test.interview.infra.dtos.UnlinkTireRequest;
import com.prologapp.test.interview.infra.services.TireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tires")
@RequiredArgsConstructor
public class TireController {

    private final TireService tireService;

    @PostMapping
    public ResponseEntity<TireResponse> create(@RequestBody CreateTireRequest request) {
        return ResponseEntity.ok(tireService.create(request));
    }

    @PostMapping("/link")
    public ResponseEntity<Void> linkTire(@RequestBody LinkTireRequest request) {
        tireService.linkTireToVehicle(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlink")
    public ResponseEntity<Void> unlinkTire(@RequestBody UnlinkTireRequest request) {
        tireService.unlinkTireFromVehicle(request);
        return ResponseEntity.ok().build();
    }

}
