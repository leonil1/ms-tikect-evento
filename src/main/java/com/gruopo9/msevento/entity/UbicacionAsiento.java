package com.gruopo9.msevento.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "ubicaciones_asientos")
@Getter
@Setter
public class UbicacionAsiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ubicacion_id")
    private Long id;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fecha_creacion", nullable = false)
    private Timestamp fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Timestamp fechaActualizacion;

    @Column(name = "usuario_actualizacion", length = 255)
    private String usuarioActualizacion;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @PrePersist
    public void setFechaCreacion() {
        this.fechaCreacion = new Timestamp(new Date().getTime());
    }
}

