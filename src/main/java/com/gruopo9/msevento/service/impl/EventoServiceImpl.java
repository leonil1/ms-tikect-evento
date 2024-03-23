package com.gruopo9.msevento.service.impl;

import com.gruopo9.msevento.domain.exception.EventoNotFoundException;
import com.gruopo9.msevento.domain.exception.EventoUtils;
import com.gruopo9.msevento.domain.util.Constants;
import com.gruopo9.msevento.entity.Evento;
import com.gruopo9.msevento.repository.EventoRepository;
import com.gruopo9.msevento.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {
    private final EventoRepository eventoRepository;

    @Override
    public ResponseEntity<String> crearEvento(Map<String, String> requestMap) {
        try {
            if (validateInputMap(requestMap)) {
                eventoRepository.save(getPersonasMap(requestMap));
                return EventoUtils.getResponseEntity("REGISTRO EXITOSO", HttpStatus.CREATED);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return EventoUtils.getResponseEntity("ERRORRRRgit RRRR", HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @Override
    public List<Evento> obtenertodoEvento() {
        return null;
    }

    private Boolean validateInputMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("titulo")
                && requestMap.containsKey("descripcion")
        ) {
            return true;
        }
        return false;
    }

    private Evento getPersonasMap(Map<String, String> requestMap) throws IOException {

        Evento evento = new Evento();
        evento.setTitulo(requestMap.get("titulo"));
        evento.setDescripcion(requestMap.get("descripcion"));
        evento.setActivo(Constants.ESTADO_ACTIVO);

        return evento;

    }

    @Override
    public Evento findById(Long id) {

        Optional<Evento> optionalEvento = eventoRepository.findById(id);
        if (optionalEvento.isPresent()) {
            try{
                return optionalEvento.get();

            }catch (Exception e){
                throw new RuntimeException(e);

            }

        } else {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }

    }

    @Override
    public ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap) {
        try {
            Optional<Evento> optionalEvento = eventoRepository.findById(id);
            if (optionalEvento.isPresent()) {
                try {
                    if (validateInputMap(requestMap)) {

                        // Actualiza otros campos si es necesario

                        eventoRepository.save(getPersonasMap(requestMap)); // Guarda el Evento actualizado en la base de datos
                        return EventoUtils.getResponseEntity("ACTUALIZACIÓN EXITOSA", HttpStatus.OK);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                return EventoUtils.getResponseEntity("Evento no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return EventoUtils.getResponseEntity("ERRORRRRRRRR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null; // No se llega aquí en condiciones normales
    }






    @Override
    public void deleteById(Long id) {
        Optional<Evento> optionalEvento = eventoRepository.findById(id);
        if (optionalEvento.isPresent()) {
            try{
                eventoRepository.deleteById(id);

            }catch (Exception e){
                EventoUtils.getResponseEntity("ERRORRRRRRRR", HttpStatus.INTERNAL_SERVER_ERROR);

            }


        } else {
            throw new RuntimeException("Evento no encontrado con ID: " + id);
        }


    }
}
