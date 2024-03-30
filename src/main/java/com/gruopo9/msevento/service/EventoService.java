package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.response.ResponseBase;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventoService {
    ResponseBase save(Evento evento);
    //Evento findEventoByTituloWithSectorAndAsientos(@Param("titulo") String titulo);


    List<Evento> listaEvento();

    Optional<Evento> findById(Long id);

    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);
}
