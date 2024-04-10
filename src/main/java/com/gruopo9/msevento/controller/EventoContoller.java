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
    public ResponseEntity<ResponseBase> createEvento(@RequestBody EventoEntity evento) {
        ResponseBase response = eventoService.save(evento);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ResponseBase> obtenerEvento(@PathVariable Long id) {
        try {
            Optional<EventoEntity> evento = eventoService.findById(id);
            return ResponseEntity.ok(ResponseBase.exitoso("Evento encontrado", evento));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseBase.errorNotFound("Evento no encontrado"));
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<ResponseBase> obtenerEvento() {
        List<EventoEntity> eventos = eventoService.listaEvento();
        return ResponseEntity.ok(ResponseBase.exitoso("Lista de eventos obtenida con exito", eventos));
    }
    @PutMapping("/update/{id}")
    public ResponseBase updateEvento(@PathVariable Long id, @RequestBody EventoEntity evento) {
        return eventoService.update(id, evento);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseBase> deleteId(@PathVariable Long id) {
        Optional<EventoEntity> evento = eventoService.findById(id);
        if (evento == null) {
            // Event not found, return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseBase.errorNotFound("Evento no encontrado"));
        }
        eventoService.deleteById(id);
        return ResponseEntity.ok(ResponseBase.exitoso("Evento eliminado con exito", null));
    }

    //actualizar asiento
    @PutMapping("/asiento/{id}/estado")
    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable Long id, @RequestParam int numeroAsiento, @RequestParam boolean estado) {
        try {
            Optional<EventoEntity> optionalEvento = eventoService.findById(id);
            if (optionalEvento.isPresent()) {
                EventoEntity evento = optionalEvento.get();
                List<SectorAsientoEntity> sectores = evento.getSector();
                for (SectorAsientoEntity sector : sectores) {
                    List<AsientoEntity> asientos = sector.getAsientos();
                    for (AsientoEntity asiento : asientos) {
                        if (asiento.getNumeroAsiento() == numeroAsiento) {
                            asiento.setEstado(estado);
                            asientoService.save(asiento);
                            return ResponseEntity.ok(ResponseBase.exitoso("Estado de asiento actualizado", asiento));
                        }
                    }
                }
            }
            return ResponseEntity.ok(ResponseBase.exitoso("Asiento no encontrado", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseBase.errorInternalSErverError("Error al cambiar estado de asiento"));
        }
    }

    //obtenet asiento
    @PutMapping("/asiento/form/{id}")
    public ResponseEntity<ResponseBase> create(@PathVariable Long id,@RequestBody AsientoEntity asiento) {
        ResponseBase response = asientoService.actualizarAsiento(id,asiento);
        return ResponseEntity.status(response.getCode()).body(response);

    }

}
