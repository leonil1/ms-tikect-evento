package com.gruopo9.msevento.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "eventos")
@Getter
@Setter
public class Evento  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evento_id")
    private Long id;



//    @NotEmpty(message = "El titulo de evento no puede ser vacío")
//    @Size( min = 4 , max = 255, message = "El tamaño del número de documento es 8")
//    @Column(name = "titulo", nullable = false, length = 255)
//    private String titulo;

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
    @Size( min = 2 , max = 10, message = "El duracion de evento debe ser entre 2 a 10 caracteres")
    @Column(name = "duracion_evento",nullable = false)
    private String duracionEvento;

    private String imagen;


    @Column(name = "activo",columnDefinition = "boolean default true")
    private boolean activo;


    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Timestamp fechaActualizacion;

    @Column(name = "usuario_actualizacion", length = 255)
    private String usuarioActualizacion;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "evento_id")
    private List<SectorAsiento> sector;


    public Evento() {
        sector = new ArrayList<>();
    }


    @PrePersist
    public void setfechaCreacion(){
        // fechaCreacion=new Date;


        this.fechaCreacion=new Timestamp(new Date().getTime());
    }


//    public void inicializarCapacidadEvento() {
//        this.capacidadEvento = sector.stream().mapToInt(SectorAsiento::getCapacidad).sum();
//    }
}
