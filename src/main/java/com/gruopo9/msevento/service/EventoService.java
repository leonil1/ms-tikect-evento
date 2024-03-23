package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.Evento;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventoService {
    ResponseEntity<String> crearEvento(Map<String,String> requestMap);

    List<Evento> obtenertodoEvento();

    Evento findById(Long id);

    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);



}
