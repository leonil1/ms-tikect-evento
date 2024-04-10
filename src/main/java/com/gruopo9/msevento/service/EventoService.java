package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.EventoEntity;
import com.gruopo9.msevento.aggregates.response.ResponseBase;

import java.util.List;
import java.util.Optional;

public interface EventoService {
    ResponseBase save(EventoEntity evento );

    List<EventoEntity> listaEvento();

    Optional<EventoEntity> findById(Long id);
    //ResponseBase<EventoEntity> findById(Long id);

    ResponseBase update(Long id, EventoEntity eventoActualizado);

    void deleteById(Long id);
}
