package com.gruopo9.msevento.repository;

import com.gruopo9.msevento.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento,Long> {
    List<Evento> findByTituloContainingIgnoreCase(String titulo);
    @Query("SELECT DISTINCT e FROM Evento e LEFT JOIN FETCH e.sector s LEFT JOIN FETCH s.asientos WHERE e.titulo = :titulo")
    Evento findEventoByTituloWithSectorAndAsientos(@Param("titulo") String titulo);

    @Query("SELECT DISTINCT e FROM Evento e JOIN FETCH e.sector s LEFT JOIN FETCH s.asientos WHERE e.id = :id")
    Evento findEventoByIdWithSectorAndAsientos(@Param("id") Long id);

    @Query("SELECT e FROM Evento e WHERE e.id = :id")
    Evento findEventoById( Long id);


}