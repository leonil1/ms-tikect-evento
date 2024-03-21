package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.domain.exception.EventoNotFoundException;
import com.gruopo9.msevento.entity.AreaAsiento;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.UbicacionAsiento;
import com.gruopo9.msevento.repository.AreaAsientoRepository;
import com.gruopo9.msevento.repository.UbicacionAsientoRepository;
import com.gruopo9.msevento.service.AreaAsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaAsientoServiceImpl implements AreaAsientoService {
    private final AreaAsientoRepository areaAsientoRepository;
    private final UbicacionAsientoRepository ubicacionAsientoRepository;
    @Override
    public AreaAsiento crear(AreaAsiento areaAsientoRequest) {
        Optional<UbicacionAsiento> optionalubicaion=ubicacionAsientoRepository.findById(areaAsientoRequest.getUbicacion().getId());
        if (optionalubicaion.isEmpty()) {
            throw new EventoNotFoundException("ubicacion no encontrado");
        }
        AreaAsiento areaAsiento=new AreaAsiento();

        areaAsiento.setNombre(areaAsientoRequest.getNombre());
        areaAsiento.setCapacidad(areaAsientoRequest.getCapacidad());
        areaAsiento.setUbicacion(optionalubicaion.get());

        return areaAsientoRepository.save(areaAsiento);

    }

    @Override
    public Optional<AreaAsiento> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public AreaAsiento actualizar(AreaAsiento evento, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
