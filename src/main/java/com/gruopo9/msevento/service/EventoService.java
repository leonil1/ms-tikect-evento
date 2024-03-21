package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.Evento;

import java.util.Optional;

public interface EventoService {
    Evento crear(Evento evento);

    Optional<Evento> findById(Long id);

    Evento actualizar(Evento evento, Long id);

    void deleteById(Long id);



}
