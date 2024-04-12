package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.entity.AsientoEntity;
import com.gruopo9.msevento.entity.EventoEntity;
import com.gruopo9.msevento.entity.SectorAsientoEntity;
import com.gruopo9.msevento.aggregates.response.ResponseBase;
import com.gruopo9.msevento.service.AsientoService;
import com.gruopo9.msevento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoContoller {

    private final EventoService eventoService;

    private final AsientoService asientoService;

    @PostMapping("/form")
    public ResponseBase createEvento(@RequestBody EventoEntity evento) {
        ResponseBase response = eventoService.save(evento);
        return response;
    }
    @GetMapping("/obtener/{id}")
    public Optional<EventoEntity> obtenerEventoId(@PathVariable Long id){
        return eventoService.findById(id);

    }

    @GetMapping("/todos")
    public ResponseBase obtenerEvento() {
        return eventoService.listaEvento();
    }


    @PutMapping("/update/{id}")
    public ResponseBase updateEvento(@PathVariable Long id, @RequestBody EventoEntity evento) {
        return eventoService.update(id, evento);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseBase deleteId(@PathVariable Long id) {
        Optional<ResponseBase> response = eventoService.deleteById(id);
        if (!response.isPresent()) {
            return ResponseBase.errorNotFound("Evento no encontrado");
        }
        return response.get();
    }


    @PutMapping("/asiento/{id}/estado")
    public ResponseBase cambiarEstadoAsiento(@PathVariable Long id, @RequestParam int numeroAsiento, @RequestParam boolean estado) {
      return   asientoService.actualizarAsiento(id, numeroAsiento,estado);
    }



}
