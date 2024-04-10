package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.entity.AsientoEntity;
import com.gruopo9.msevento.repository.AsientoRepository;
import com.gruopo9.msevento.aggregates.response.ResponseBase;
import com.gruopo9.msevento.service.AsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsientoServiceImpl implements AsientoService {
    private final AsientoRepository asientoRepository;
    @Override
    public AsientoEntity save(AsientoEntity asiento) {

        return asientoRepository.save(asiento);
    }

    @Override
    public ResponseBase actualizarAsiento(Long id, AsientoEntity asientoR) {
        try {
            Optional<AsientoEntity> optionalAsiento = asientoRepository.findById(id);
            if (optionalAsiento.isPresent()) {
                AsientoEntity asiento = optionalAsiento.get();
                asiento.setEstado(asientoR.isEstado());
                // Actualiza otros campos según sea necesario
                asientoRepository.save(asiento);
                return ResponseBase.exitoso("Asiento actualizado correctamente", Optional.of(asiento.getId()));
            } else {
                return ResponseBase.errorNotFound("El asiento con ID " + id + " no se encontró");
            }
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud");
        }
    }

    @Override
    public List<AsientoEntity> obtenertodoEvento() {
        return null;
    }

    @Override
    public AsientoEntity findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
