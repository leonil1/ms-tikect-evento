package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.AsientoEntity;
import com.gruopo9.msevento.aggregates.response.ResponseBase;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AsientoService {
    AsientoEntity save(AsientoEntity asiento);

    ResponseBase actualizarAsiento(Long id, AsientoEntity asientoRequest);
    List<AsientoEntity> obtenertodoEvento();
    AsientoEntity findById(Long id);


    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);
}