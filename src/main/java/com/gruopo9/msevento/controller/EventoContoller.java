package com.gruopo9.msevento.controller;

import com.gruopo9.msevento.entity.AsientoEntity;
import com.gruopo9.msevento.entity.EventoEntity;
import com.gruopo9.msevento.aggregates.response.ResponseBase;
import com.gruopo9.msevento.repository.EventoRepository;
import com.gruopo9.msevento.repository.IUploadFileService;
import com.gruopo9.msevento.service.AsientoService;
import com.gruopo9.msevento.service.EventoService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
@RefreshScope
public class EventoContoller {

    private final EventoService eventoService;

    private final AsientoService asientoService;

    private final EventoRepository eventoRepository;

    private IUploadFileService uploadService;

    @PostMapping("/form")

    public ResponseBase createEvento(@RequestBody EventoEntity evento) {
        ResponseBase response = eventoService.save(evento);
        return response;
    }

    @GetMapping("/obtener/{id}")

    public Optional<EventoEntity> obtenerEventoId(@PathVariable Long id) {
        return eventoService.findById(id);

    }

    @GetMapping("/todos")

    public ResponseBase obtenerEvento() {
        return eventoService.listaEvento();
    }

    @GetMapping("/paginado")
    public ResponseBase obtenerEventoPaninado() {
        Pageable pageable = PageRequest.of(0, 5); // Página 0, tamaño de página 5
        return eventoService.listaEvento(pageable);
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
        return asientoService.actualizarAsiento(id, numeroAsiento, estado);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        Optional<EventoEntity> eventoOptional = eventoService.findById(id);

        if (eventoOptional.isPresent()) {
            EventoEntity evento = eventoOptional.get();

            if (!archivo.isEmpty()) {
                String nombreArchivo = null;
                try {
                    nombreArchivo = uploadService.copiar(archivo);
                } catch (IOException e) {
                    response.put("mensaje", "Error al subir la imagen del cliente");
                    response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }

                String nombreFotoAnterior = evento.getImagen();

                uploadService.eliminar(nombreFotoAnterior);

                evento.setImagen(nombreArchivo);

                eventoRepository.save(evento);

                response.put("cliente", evento);
                response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
            } else {
                response.put("mensaje", "El archivo está vacío");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            response.put("mensaje", "No se encontró el evento con ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
