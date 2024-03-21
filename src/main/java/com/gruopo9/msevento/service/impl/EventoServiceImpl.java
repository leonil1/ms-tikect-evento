package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.repository.EventoRepository;
import com.gruopo9.msevento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {
    private final EventoRepository eventoRepository;
    @Override
    public Evento crear(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Evento actualizar(Evento evento, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
