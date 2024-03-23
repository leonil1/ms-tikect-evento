package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.domain.exception.EventoUtils;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @PostMapping("/form")
    public ResponseEntity<String> crear(@RequestBody(required = true) Map<String,String> requestMap) {
        try {
            return eventoService.crearEvento(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return EventoUtils.getResponseEntity("ERRORRRRR", HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PostMapping("/form/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody Map<String, String> requestMap) {
        try {
            // Llama al método de actualización en el servicio con el ID y los campos actualizados
            return eventoService.actualizar(id, requestMap);
        } catch (Exception e) {
            e.printStackTrace();
            return EventoUtils.getResponseEntity("ERRORRRRR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Evento> obtenerEvento(@PathVariable Long id){
       return ResponseEntity.ok(eventoService.findById(id)) ;
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> eliminarEvento(@PathVariable Long id){
        eventoService.deleteById(id);
        return ResponseEntity.ok().body("Eliminado con exito") ;
    }




}
