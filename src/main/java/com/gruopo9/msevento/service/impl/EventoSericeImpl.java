package com.gruopo9.msevento.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruopo9.msevento.entity.Asiento;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.entity.SectorAsiento;
import com.gruopo9.msevento.repository.EventoRepository;
import com.gruopo9.msevento.response.ResponseBase;
import com.gruopo9.msevento.service.EventoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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

    @Override
    public ResponseBase save(Evento evento) {
        try {
            validarCapacidadYCrearAsientos(evento);
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
    public Evento findById(Long id) {
        try {
            Evento evento = eventoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));
            return evento;
        } catch (EntityNotFoundException e) {
            // Manejar la excepción de entidad no encontrada
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el evento", e);
        }
    }

    //  @Override
//    public Evento findById(Long id) {
//        try {
//            Evento evento = eventoRepository.findEventoById(id);
//            if (evento == null) {
//                throw new EntityNotFoundException("Evento no encontrado");
//            }
//            return evento;
//        } catch (EntityNotFoundException e) {
//            // Manejar la excepción de entidad no encontrada
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (Exception e) {
//            // Manejar cualquier otra excepción
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el evento", e);
//        }
//    }


//    @Override
//    public Evento findById(Long id) {
//        Evento evento = eventoRepository.findEventoById(id);
//
//        return evento;
//    }



//    @Override
//    public Evento findEventoByTituloWithSectorAndAsientos(String titulo) {
//        return eventoRepository.findEventoByTituloWithSectorAndAsientos(titulo);
//    }


    //    @Override
//    public ResponseBase save(Evento evento) {
//        try {
//            // Validar la capacidad de cada sector de asiento
//            for (SectorAsiento sectorAsiento : evento.getSector()) {
//                if (sectorAsiento.getCapacidad() <= 0) {
//                    return ResponseBase.error("La capacidad de un sector de asiento debe ser mayor que cero", HttpStatus.BAD_REQUEST);
//                }
//
//                // Crear los asientos y asignar los números de asiento y descripciones
//                List<Asiento> asientos = new ArrayList<>();
//                for (Asiento asientoDetalle : sectorAsiento.getAsientos()) {
//                    Asiento asiento = new Asiento();
//                    asiento.setNumeroAsiento(asientoDetalle.getNumeroAsiento());
//                    asiento.setDescripcion(asientoDetalle.getDescripcion());
//                    asiento.setSectorAsiento(sectorAsiento); // Establecer la relación
//                    asientos.add(asiento);
//                }
//                sectorAsiento.setAsientos(asientos);
//            }
//
//
//            // Guardar el evento y sus asientos
//            eventoRepository.save(evento);
//
//            // Retornar una respuesta exitosa
//            return ResponseBase.exitoso("Evento creado correctamente", Optional.of(evento.getId()));
//        } catch (ConstraintViolationException e) {
//            return ResponseBase.error("Error de validación: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return ResponseBase.error("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

//    @Override
//    public Evento findById(Long id) {
//        Evento evento = eventoRepository.findEventoById(id);
//        if (evento != null) {
//            evento.getSector().size(); // Inicializa la relación SectorAsiento
//            for (SectorAsiento sectorAsiento : evento.getSector()) {
//                sectorAsiento.getAsientos().size(); // Inicializa la relación Asiento
//            }
//        }
//        return evento;
//    }
//    @Override
//    public Evento findById(Long id) {
//        Optional<Evento> eventoOptional = eventoRepository.findById(id);
//        if (eventoOptional.isPresent()) {
//            Evento evento = eventoOptional.get();
//            evento.getSector().size(); // Inicializa la relación SectorAsiento
//            for (SectorAsiento sectorAsiento : evento.getSector()) {
//                sectorAsiento.getAsientos().size(); // Inicializa la relación Asiento
//            }
//            return evento;
//        }
//        return null;
//    }

    @Override
    public ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        eventoRepository.deleteById(id);

    }
}
