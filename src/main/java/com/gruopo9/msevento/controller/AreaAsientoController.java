package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.entity.AreaAsiento;
import com.gruopo9.msevento.entity.UbicacionAsiento;
import com.gruopo9.msevento.service.AreaAsientoService;
import com.gruopo9.msevento.service.UbicacionAsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaAsientoController {
    private final AreaAsientoService areaAsientoService;
    @PostMapping("/form")
    public ResponseEntity<AreaAsiento> crear(@RequestBody AreaAsiento areaAsiento) {
        AreaAsiento areaCreada = areaAsientoService.crear(areaAsiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(areaCreada);
    }
}
