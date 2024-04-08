package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.entity.Asiento;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.SectorAsiento;
import com.gruopo9.msevento.response.ResponseBase;
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
    public ResponseEntity<ResponseBase> createEvento(@RequestBody Evento evento) {
        ResponseBase response = eventoService.save(evento);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ResponseBase> obtenerEvento(@PathVariable Long id) {
        try {
            Optional<Evento> evento = eventoService.findById(id);
            return ResponseEntity.ok(ResponseBase.exitoso("Evento encontrado", evento));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseBase.error("Evento no encontrado", HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<ResponseBase> obtenerEvento() {
        List<Evento> eventos = eventoService.listaEvento();
        return ResponseEntity.ok(ResponseBase.exitoso("Lista de eventos obtenida con exito", eventos));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseBase> deleteId(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.findById(id);
        if (evento == null) {
            // Event not found, return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseBase.error("Evento no encontrado", HttpStatus.NOT_FOUND));
        }
        eventoService.deleteById(id);
        return ResponseEntity.ok(ResponseBase.exitoso("Evento eliminado con exito", null));
    }

    //actualizar asiento
    @PutMapping("/asiento/{id}/estado")
    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable Long id, @RequestParam int numeroAsiento, @RequestParam boolean estado) {
        try {
            Optional<Evento> optionalEvento = eventoService.findById(id);
            if (optionalEvento.isPresent()) {
                Evento evento = optionalEvento.get();
                List<SectorAsiento> sectores = evento.getSector();
                for (SectorAsiento sector : sectores) {
                    List<Asiento> asientos = sector.getAsientos();
                    for (Asiento asiento : asientos) {
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
                    .body(ResponseBase.error("Error al cambiar estado de asiento", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    //obtenet asiento
    @PutMapping("/asiento/form/{id}")
    public ResponseEntity<ResponseBase> create(@PathVariable Long id,@RequestBody Asiento asiento) {
        ResponseBase response = asientoService.actualizarAsiento(id,asiento);
        return ResponseEntity.status(response.getCode()).body(response);

    }

}
