package com.gruopo9.msevento.repository;

import com.gruopo9.msevento.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento,Long> {
}
