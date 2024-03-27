package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.Asiento;
import com.gruopo9.msevento.response.ResponseBase;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AsientoService {
    Asiento save(Asiento asiento);


    ResponseBase actualizarAsiento(Long id, Asiento asientoRequest);
    List<Asiento> obtenertodoEvento();

    Asiento findById(Long id);

    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);
}