package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    public ResponseEntity<Evento> crear(@RequestBody Evento eventoRequest) {
        Evento eventoCreado = eventoService.crear(eventoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCreado);
    }


}
