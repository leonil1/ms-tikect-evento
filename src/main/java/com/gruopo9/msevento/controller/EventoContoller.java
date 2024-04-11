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
    public ResponseBase obtenerEvento(@PathVariable Long id) {
        Optional<ResponseBase> evento = eventoService.findById(id);

        if (evento.isPresent()) {
            return evento.get();
        } else {
            return ResponseBase.errorNotFound("Evento no encontrado");
        }
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


    //actualizar asiento
//    @PutMapping("/asiento/{id}/estado")
//    public ResponseBase cambiarEstadoAsiento(@PathVariable Long id, @RequestParam int numeroAsiento, @RequestParam boolean estado) {
//        try {
//            Optional<ResponseBase> optionalEvento = eventoService.findById(id);
//            if (optionalEvento.isPresent()) {
//                EventoEntity evento = optionalEvento.get();
//                List<SectorAsientoEntity> sectores = evento.getSector();
//                for (SectorAsientoEntity sector : sectores) {
//                    List<AsientoEntity> asientos = sector.getAsientos();
//                    for (AsientoEntity asiento : asientos) {
//                        if (asiento.getNumeroAsiento() == numeroAsiento) {
//                            asiento.setEstado(estado);
//                            asientoService.save(asiento);
//                            return ResponseBase.exitoso("Estado de asiento actualizado", asiento);
//                        }
//                    }
//                }
//            }
//            return ResponseBase.errorNotFound("Asiento no encontrado");
//        } catch (Exception e) {
//            return ResponseBase.errorInternalSErverError("Error al cambiar estado de asiento");
//        }
//    }

    //actualizar asiento
    @PutMapping("/asiento/{id}/estado")
    public ResponseBase cambiarEstadoAsiento(@PathVariable Long id, @RequestParam int numeroAsiento, @RequestParam boolean estado) {
        try {
            Optional<ResponseBase> optionalEvento = eventoService.findById(id);
            if (optionalEvento.isPresent()) {
                EventoEntity evento = (EventoEntity) optionalEvento.get().getData().orElse(null);
                List<SectorAsientoEntity> sectores = evento.getSector();
                for (SectorAsientoEntity sector : sectores) {
                    List<AsientoEntity> asientos = sector.getAsientos();
                    for (AsientoEntity asiento : asientos) {
                        if (asiento.getNumeroAsiento() == numeroAsiento) {
                            asiento.setEstado(estado);
                            asientoService.save(asiento);
                            return ResponseBase.exitoso("Estado de asiento actualizado", asiento);
                        }
                    }
                }
            }
            return ResponseBase.errorNotFound("Asiento no encontrado");
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al cambiar estado de asiento");
        }
    }



    //obtenet asiento
    @PutMapping("/asiento/form/{id}")
    public ResponseEntity<ResponseBase> create(@PathVariable Long id,@RequestBody AsientoEntity asiento) {
        ResponseBase response = asientoService.actualizarAsiento(id,asiento);
        return ResponseEntity.status(response.getCode()).body(response);

    }

}
