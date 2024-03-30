package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.entity.Asiento;
import com.gruopo9.msevento.repository.AsientoRepository;
import com.gruopo9.msevento.response.ResponseBase;
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
    public Asiento save(Asiento asiento) {

        return asientoRepository.save(asiento);
    }

    @Override
    public ResponseBase actualizarAsiento(Long id, Asiento asientoR) {
        try {
            Optional<Asiento> optionalAsiento = asientoRepository.findById(id);
            if (optionalAsiento.isPresent()) {
                Asiento asiento = optionalAsiento.get();
                asiento.setEstado(asientoR.isEstado());
                // Actualiza otros campos según sea necesario
                asientoRepository.save(asiento);
                return ResponseBase.exitoso("Asiento actualizado correctamente", Optional.of(asiento.getId()));
            } else {
                return ResponseBase.error("El asiento con ID " + id + " no se encontró", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseBase.error("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public List<Asiento> obtenertodoEvento() {
        return null;
    }

    @Override
    public Asiento findById(Long id) {
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
