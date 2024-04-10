package com.gruopo9.msevento.repository;

import com.gruopo9.msevento.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventoRepository extends JpaRepository<EventoEntity,Long> {
    List<EventoEntity> findByTituloContainingIgnoreCase(String titulo);
    @Query("SELECT DISTINCT e FROM EventoEntity e LEFT JOIN FETCH e.sector s LEFT JOIN FETCH s.asientos WHERE e.titulo = :titulo")
    EventoEntity findEventoByTituloWithSectorAndAsientos(@Param("titulo") String titulo);

    @Query("SELECT DISTINCT e FROM EventoEntity e JOIN FETCH e.sector s LEFT JOIN FETCH s.asientos WHERE e.id = :id")
    EventoEntity findEventoByIdWithSectorAndAsientos(@Param("id") Long id);

    @Query("SELECT e FROM EventoEntity e WHERE e.id = :id")
    EventoEntity findEventoById(Long id);


}