package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.AreaAsiento;
import com.gruopo9.msevento.entity.Evento;

import java.util.Optional;

public interface AreaAsientoService {
    AreaAsiento crear(AreaAsiento evento);

    Optional<AreaAsiento> findById(Long id);

    AreaAsiento actualizar(AreaAsiento evento, Long id);

    void deleteById(Long id);
}
