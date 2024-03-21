package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.domain.exception.EventoNotFoundException;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.UbicacionAsiento;
import com.gruopo9.msevento.repository.EventoRepository;
import com.gruopo9.msevento.repository.UbicacionAsientoRepository;
import com.gruopo9.msevento.service.UbicacionAsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbicacionAsientoServiceImpl implements UbicacionAsientoService {

    private final UbicacionAsientoRepository ubicacionAsientoRepository;
    private final EventoRepository eventoRepository;
    @Override
    public UbicacionAsiento crear(UbicacionAsiento ubicacionAsiento) {
        Optional<Evento> optionalEvento=eventoRepository.findById(ubicacionAsiento.getEvento().getId());
        if (optionalEvento.isEmpty()) {
            throw new EventoNotFoundException("Evento no encontrado");
        }
        UbicacionAsiento ubicacionAsiento1=new UbicacionAsiento();

        ubicacionAsiento1.setDescripcion(ubicacionAsiento.getDescripcion());
        ubicacionAsiento1.setEvento(optionalEvento.get());

        return ubicacionAsientoRepository.save(ubicacionAsiento1);
    }

    @Override
    public Optional<UbicacionAsiento> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UbicacionAsiento actualizar(UbicacionAsiento evento, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
