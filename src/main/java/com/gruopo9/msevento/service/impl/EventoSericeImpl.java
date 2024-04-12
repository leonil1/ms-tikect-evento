package com.gruopo9.msevento.service.impl;
import com.gruopo9.msevento.aggregates.response.ResponseBase;
import com.gruopo9.msevento.client.UsuarioClient;
import com.gruopo9.msevento.entity.AsientoEntity;
import com.gruopo9.msevento.entity.EventoEntity;
import com.gruopo9.msevento.entity.SectorAsientoEntity;
import com.gruopo9.msevento.exception.ResourceNotFoundException;
import com.gruopo9.msevento.repository.AsientoRepository;
import com.gruopo9.msevento.repository.EventoRepository;

import com.gruopo9.msevento.repository.SectorAsientoRepository;
import com.gruopo9.msevento.service.EventoService;
import com.gruopo9.msevento.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class EventoSericeImpl implements EventoService {
    private final EventoRepository eventoRepository;
    private final AsientoRepository asientoRepository;
    private final SectorAsientoRepository sectorAsientoRepository;

    private final UsuarioClient usuarioClient;


    @Autowired
    private HttpServletRequest request;

    @Override
    public ResponseBase save(EventoEntity evento) {
        try {
            String token = request.getHeader("Authorization");
            Usuario usuarioDTO = usuarioClient.getUsuarioAutenticado(token);
            evento.setUserCreate(usuarioDTO.getUsername());
            eventoRepository.save(evento);
            return ResponseBase.exitoso("Evento creado correctamente", Optional.of(evento.getId()));
        } catch (ConstraintViolationException e) {
            return ResponseBase.errorViolationException("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud "+e.getMessage());
        }
    }


    @Override
    public ResponseBase update(Long id, EventoEntity eventoActualizado) {
        try {
            String token = request.getHeader("Authorization");
            Usuario usuarioDTO = usuarioClient.getUsuarioAutenticado(token);

            EventoEntity eventoExistente = eventoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));

            // Update event properties
            eventoExistente.setTitulo(eventoActualizado.getTitulo());
            eventoExistente.setDescripcion(eventoActualizado.getDescripcion());
            eventoExistente.setFechaEvento(eventoActualizado.getFechaEvento());
            eventoExistente.setHoraEvento(eventoActualizado.getHoraEvento());
            eventoExistente.setDuracionEvento(eventoActualizado.getDuracionEvento());
            eventoExistente.setTipoPublico(eventoActualizado.getTipoPublico());
            eventoExistente.setUserModif(usuarioDTO.getUsername());
            eventoExistente.setDateModif(new Date());

            // Update sectors and seats (assuming sector list order matches)
            List<SectorAsientoEntity> sectoresActualizados = eventoActualizado.getSector();
            for (int i = 0; i < sectoresActualizados.size(); i++) {
                SectorAsientoEntity sectorActualizado = sectoresActualizados.get(i);
                SectorAsientoEntity sectorExistente = eventoExistente.getSector().get(i);

                // Update sector properties
                sectorExistente.setNombreSector(sectorActualizado.getNombreSector());
                sectorExistente.setPrecio(sectorActualizado.getPrecio());
                sectorExistente.setCapacidadSector(sectorActualizado.getCapacidadSector());
                sectorExistente.setDescripcionAsientos(sectorActualizado.getDescripcionAsientos());

                // Update seats within the sector
                List<AsientoEntity> asientosActualizados = sectorActualizado.getAsientos();
                for (int j = 0; j < asientosActualizados.size(); j++) {
                    AsientoEntity asientoActualizado = asientosActualizados.get(j);
                    AsientoEntity asientoExistente = sectorExistente.getAsientos().get(j);

                    asientoExistente.setNumeroAsiento(asientoActualizado.getNumeroAsiento());
                    asientoExistente.setEstado(asientoActualizado.isEstado());
                }
            }

            // Save changes to the database
            eventoRepository.save(eventoExistente);

            return ResponseBase.exitoso("Evento actualizado correctamente", Optional.of(eventoExistente.getId()));
        } catch (ResourceNotFoundException e) {
            return ResponseBase.errorNotFound("Recurso no encontrado: " + e.getMessage());
        } catch (ConstraintViolationException e) {
            return ResponseBase.errorViolationException("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud");
        }
    }


    @Override
    public ResponseBase listaEvento() {
        try {
            List<EventoEntity> eventos = eventoRepository.findAll();
            return ResponseBase.exitoso("Lista de eventos obtenida con éxito", eventos);
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al obtener la lista de eventos");
        }
    }

    @Override
    public Optional<EventoEntity> findById(Long id) {
        return eventoRepository.findById(id);
    }


//    @Override
//    public Optional<ResponseBase> findById(Long id) {
//        try {
//            Optional<EventoEntity> evento = eventoRepository.findById(id);
//            if (evento.isPresent()) {
//                return Optional.of(ResponseBase.exitoso("Evento encontrado", evento.get()));
//            } else {
//                return Optional.of(ResponseBase.errorNotFound("Evento no encontrado"));
//            }
//        } catch (Exception e) {
//            return Optional.of(ResponseBase.errorNotFound("Error al buscar el evento"));
//        }
//    }



    @Override
    public Optional<ResponseBase> deleteById(Long id) {
        try {
            Optional<EventoEntity> evento = eventoRepository.findById(id);
            if (!evento.isPresent()) {
                return Optional.empty(); // Evento no encontrado
            }
            eventoRepository.deleteById(id);
            return Optional.of(ResponseBase.exitoso("Evento eliminado con éxito", null));
        } catch (Exception e) {
            return Optional.of(ResponseBase.errorInternalSErverError("Error al eliminar el evento"));
        }
    }

}
