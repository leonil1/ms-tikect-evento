package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.SectorAsientoEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SectorAsientoService {
    ResponseEntity<String> crearEvento(Map<String,String> requestMap);
    SectorAsientoEntity save(SectorAsientoEntity sectorAsiento);
    List<SectorAsientoEntity> obtenertodoEvento();

    SectorAsientoEntity findById(Long id);

    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);
}
