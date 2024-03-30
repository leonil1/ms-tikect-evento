package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.entity.Asiento;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.SectorAsiento;
import com.gruopo9.msevento.response.ResponseBase;
import com.gruopo9.msevento.service.AsientoService;
import com.gruopo9.msevento.service.EventoService;
import com.gruopo9.msevento.service.UploadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoContoller {

    private final EventoService eventoService;
    //private final AsientoService asientoService;
    private final UploadService uploadService;

    private final AsientoService asientoService;




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


    @PutMapping("/asiento/form/{id}")
    public ResponseEntity<ResponseBase> create(@PathVariable Long id,@RequestBody Asiento asiento) {
        ResponseBase response = asientoService.actualizarAsiento(id,asiento);
        return ResponseEntity.status(response.getCode()).body(response);

    }



//    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable Long id, @RequestParam boolean estado) {
//        Asiento asiento = asientoService.findById(id);
//        if (asiento == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(ResponseBase.error("Asiento no encontrado con el ID: " + id, HttpStatus.NOT_FOUND));
//        }
//
//        asiento.setEstado(estado);
//        asientoService.save(asiento);
//
//        return ResponseEntity.ok().body(ResponseBase.exitoso("Estado del asiento cambiado correctamente", asiento.getId()));
//    }




//    @PostMapping("/form")
//    public ResponseEntity<ResponseBase> create(@RequestBody Evento evento) {
//        return ResponseEntity.ok(eventoService.save(evento));
//    }

    @PostMapping("/form")
    public ResponseEntity<ResponseBase> createASiento(@RequestBody Evento evento) {
        ResponseBase response = eventoService.save(evento);
        return ResponseEntity.status(response.getCode()).body(response);

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
        // Event found, delete and return success message
        return ResponseEntity.ok(ResponseBase.exitoso("Evento eliminado con exito", null));
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

//    @PutMapping("/updateAsiento/{id}")
//    public ResponseEntity<ResponseBase> atualizarAsiento(@PathVariable Long id, @RequestBody Asiento asiento) {
//        try {
//            ResponseBase response = asientoService.actualizarAsiento(id, asiento);
//            if (response.getEstado()) {
//                return ResponseEntity.ok(response);
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ResponseBase.error("Error al actualizar el asiento", HttpStatus.INTERNAL_SERVER_ERROR));
//        }
//    }

//    @PostMapping("/upload")
//    public ResponseEntity<ResponseBase> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
//        Optional<Evento> evento = eventoService.findById(id);
//        if (!archivo.isEmpty()) {
//            String nombreArchivo = null;
//            try {
//                nombreArchivo = uploadService.copiar(archivo);
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBase.error("Error al subir la imagen del cliente", HttpStatus.INTERNAL_SERVER_ERROR));
//            }
//
//            String nombreFotoAnterior = evento.getImagen();
//
//            uploadService.eliminar(nombreFotoAnterior);
//
//            evento.setImagen(nombreArchivo);
//
//            eventoService.save(evento);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBase.exitoso("Has subido correctamente la imagen: " + nombreArchivo, Optional.of(evento)));
//        }
//
//        return ResponseEntity.badRequest().body(ResponseBase.error("El archivo está vacío", HttpStatus.BAD_REQUEST));
//    }









//    @GetMapping("/obtener/{id}")
//    public ResponseEntity<?> obtenerEvento(@PathVariable Long id) {
//        Evento eventos = eventoService.findById(id);
//        return ResponseEntity.ok(eventos);
//    }

    //    @GetMapping("/{titulo}")
//    public ResponseEntity<?> buscarPorTitulo(@PathVariable String titulo) {
//        Evento eventos = eventoService.findEventoByTituloWithSectorAndAsientos(titulo);
//        return ResponseEntity.ok(eventos);
//    }

//    @GetMapping("/todos")
//    public ResponseEntity<?> obtenerEvento() {
//        List<Evento> eventos = eventoService.listaEvento();
//        return ResponseEntity.ok(eventos);
//    }


//    @GetMapping("/buscarPorTitulo")
//    public ResponseEntity<List<Evento>> buscarPorTitulo(@RequestParam String titulo) {
//        List<Evento> eventos = eventoService.buscarPorTitulo(titulo);
//        if (eventos.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(eventos);
//        }
//    }
//    @GetMapping("/obtener/{id}")
//    public ResponseEntity<ResponseBase> obtenerAsientoPorId(@PathVariable Long id) {
//        Asiento asiento = asientoService.findById(id);
//        if (asiento != null) {
//            // El asiento se encontró, construye la respuesta exitosa
//            return ResponseEntity.ok(ResponseBase.exitoso("Asiento encontrado", Optional.of(asiento)));
//        } else {
//            // El asiento no se encontró, devuelve un error 404
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(ResponseBase.error("Asiento no encontrado", HttpStatus.NOT_FOUND));
//        }
//    }


//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ResponseBase> deleteId(@PathVariable Long id) {
//        Evento evento=eventoService.findById(id);
//        if (evento != null) {
//            eventoService.deleteById(id);
//            // El asiento se encontró, construye la respuesta exitosa
//            return ResponseEntity.ok(ResponseBase.exitoso("Evento eliminado con exito", Optional.of(null)));
//        } else {
//            // El asiento no se encontró, devuelve un error 404
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(ResponseBase.error("Erro al aliminar evento", HttpStatus.NOT_FOUND));
//        }
//    }




//    @PostMapping("/form")
//    public ResponseEntity<?> create(@RequestBody Evento evento) {
//
//        Evento clienteNew = null;
//        Map<String, Object> response = new HashMap<>();
//
//
//
//        try {
//            clienteNew = eventoService.save(evento);
//        } catch(DataAccessException e) {
//            response.put("mensaje", "Error al realizar el insert en la base de datos");
//            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        response.put("mensaje", "El cliente ha sido creado con éxito!");
//        response.put("cliente", clienteNew);
//        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//    }
}
