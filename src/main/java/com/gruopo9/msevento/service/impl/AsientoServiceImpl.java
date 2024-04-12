package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.entity.AsientoEntity;
import com.gruopo9.msevento.entity.EventoEntity;
import com.gruopo9.msevento.entity.SectorAsientoEntity;
import com.gruopo9.msevento.repository.AsientoRepository;
import com.gruopo9.msevento.aggregates.response.ResponseBase;
import com.gruopo9.msevento.repository.EventoRepository;
import com.gruopo9.msevento.service.AsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsientoServiceImpl implements AsientoService {
    private final AsientoRepository asientoRepository;
    private final EventoRepository eventoRepository;

    public ResponseBase actualizarAsiento(Long id, int numeroAsiento,boolean estado){
        try {
            Optional<EventoEntity> optionalEvento = eventoRepository.findById(id);
            if (optionalEvento.isPresent()) {
                EventoEntity evento = optionalEvento.get();
                List<SectorAsientoEntity> sectores = evento.getSector();
                for (SectorAsientoEntity sector : sectores) {
                    List<AsientoEntity> asientos = sector.getAsientos();
                    for (AsientoEntity asiento : asientos) {
                        if (asiento.getNumeroAsiento() == numeroAsiento) {
                            asiento.setEstado(estado);
                            asientoRepository.save(asiento);
                            return ResponseBase.exitoso("Estado de asiento actualizado", asiento);
                        }
                    }
                }
            }
            return ResponseBase.errorNotFound("Asiento no encontrado");
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al cambiar estado de asiento");
        }
    }
}
