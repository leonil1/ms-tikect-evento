package com.gruopo9.msevento.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gruopo9.msevento.entity.common.Audit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "eventos")
@Getter
@Setter
public class EventoEntity extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El título de evento no puede ser vacío")
    @Size(min = 4, max = 255, message = "El tamaño del título debe estar entre 4 y 255 caracteres")
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @NotEmpty(message = "La descripcion de evento no puede ser vacío")
    @Size( min = 4 , max = 255, message = "El tamaño debe ser entre 4 y 255")
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "fecha_evento", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEvento;

    @Column(name = "hora_evento", nullable = true)
    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime horaEvento;


    @NotEmpty(message = "La duaracion de evento no puede ser vacío")
    @Size( min = 4 , max = 10, message = "El duracion de evento debe ser entre 2 a 10 caracteres")
    @Column(name = "duracion_evento",nullable = false)
    private String duracionEvento;

    @NotEmpty(message = "El tipo publico  de evento no puede ser vacío")
    @Size( min = 2 , max = 10, message = "El tipo publico debe ser entre 2 a 10 caracteres")
    @Column(name = "tipo_publico",nullable = false)
    private String tipoPublico;

    private String imagen;

    @Column(name = "activo")
    private boolean activo;

    public EventoEntity(String userCreate, Date dateCreate, String userModif, Date dateModif, String userDelet, Date dateDelet) {
        super(userCreate, dateCreate, userModif, dateModif, userDelet, dateDelet);
    }

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "evento_id")
    private List<SectorAsientoEntity> sector;

    public EventoEntity() {
        sector = new ArrayList<>();
    }
    @PrePersist
    protected void inicializarPorDefecto() {
        this.activo=true;
        setDateCreate(new Date());

    }

}
