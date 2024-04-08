package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.EntityDto.UsuarioDTO;
import com.gruopo9.msevento.client.UsuarioClient;
import com.gruopo9.msevento.entity.Asiento;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.SectorAsiento;
import com.gruopo9.msevento.repository.EventoRepository;
import com.gruopo9.msevento.response.ResponseBase;
import com.gruopo9.msevento.service.EventoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class EventoSericeImpl implements EventoService {
    private final EventoRepository eventoRepository;

    private final UsuarioClient usuarioClient;

    @Autowired
    private HttpServletRequest request;

    @Override
    public ResponseBase save(Evento evento) {
        try {
            String token = request.getHeader("Authorization");
            UsuarioDTO usuarioDTO = usuarioClient.getUsuarioAutenticado(token);
            validarCapacidadYCrearAsientos(evento);
            evento.setUsuarioCreador(usuarioDTO.getUsername());
            evento.setUsuarioCreador(usuarioDTO.getUsername());
            eventoRepository.save(evento);
            return ResponseBase.exitoso("Evento creado correctamente", Optional.of(evento.getId()));
        } catch (ConstraintViolationException e) {
            return ResponseBase.error("Error de validación: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseBase.error("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void validarCapacidadYCrearAsientos(Evento evento) {
        for (SectorAsiento sectorAsiento : evento.getSector()) {
            if (sectorAsiento.getCapacidad() <= 0) {
                throw new ConstraintViolationException("La capacidad de un sector de asiento debe ser mayor que cero", null);
            }

            List<Asiento> asientos = new ArrayList<>();
            for (Asiento asientoDetalle : sectorAsiento.getAsientos()) {
                Asiento asiento = new Asiento();
                asiento.setNumeroAsiento(asientoDetalle.getNumeroAsiento());
                asiento.setDescripcion(asientoDetalle.getDescripcion());
                asiento.setSectorAsiento(sectorAsiento); // Establecer la relación
                asientos.add(asiento);
            }
            sectorAsiento.setAsientos(asientos);
        }
    }

    @Override
    public List<Evento> listaEvento() {
        return eventoRepository.findAll();
    }


    @Override
    public Optional<Evento> findById(Long id) {
        try {
            return eventoRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el evento", e);
        }
    }

    @Override
    public ResponseBase actualizar(Long id, Evento evento) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        eventoRepository.deleteById(id);

    }
}
