package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.UbicacionAsiento;
import com.gruopo9.msevento.service.EventoService;
import com.gruopo9.msevento.service.UbicacionAsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ubicacion")
@RequiredArgsConstructor
public class UicacionAsientoController {
    private final UbicacionAsientoService ubicacionAsientoService;
    @PostMapping("/form")
    public ResponseEntity<UbicacionAsiento> crear(@RequestBody UbicacionAsiento ubicacionAsiento) {
        UbicacionAsiento ubicacionCreada = ubicacionAsientoService.crear(ubicacionAsiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionCreada);
    }


}
