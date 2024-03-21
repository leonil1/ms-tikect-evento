package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.UbicacionAsiento;

import java.util.Optional;

public interface UbicacionAsientoService {
    UbicacionAsiento crear(UbicacionAsiento evento);

    Optional<UbicacionAsiento> findById(Long id);

    UbicacionAsiento actualizar(UbicacionAsiento evento, Long id);

    void deleteById(Long id);
}
