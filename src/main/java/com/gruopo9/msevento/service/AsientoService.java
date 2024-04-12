package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.AsientoEntity;
import com.gruopo9.msevento.aggregates.response.ResponseBase;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AsientoService {
        ResponseBase actualizarAsiento(Long id, int numeroAsiento,boolean estado);

}