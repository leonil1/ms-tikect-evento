package com.gruopo9.msevento.service;

import com.gruopo9.msevento.entity.SectorAsiento;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SectorAsientoService {
    ResponseEntity<String> crearEvento(Map<String,String> requestMap);
    SectorAsiento save(SectorAsiento sectorAsiento);
    List<SectorAsiento> obtenertodoEvento();

    SectorAsiento findById(Long id);

    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);
}
