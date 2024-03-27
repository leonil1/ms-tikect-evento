package com.gruopo9.msevento.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "asientos")
@Getter
@Setter
public class Asiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_asiento_id", nullable = false)
    private SectorAsiento sectorAsiento;

    @Min(value = 0, message = "El numero de asiento debe ser mayor  a cero")
    @Column(name = "numero_asiento", nullable = false)
    private int numeroAsiento;


    @Size( min = 2 , max = 50, message = "El tamaño del número de documento es 5")
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "vendido", columnDefinition = "boolean default false")
    private boolean estado;
}