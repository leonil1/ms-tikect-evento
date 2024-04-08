package com.gruopo9.msevento.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "sector_asiento")
@Getter
@Setter
public class SectorAsiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede ser vacío")
    @Size(min = 4, max = 255, message = "El tamaño de nombre debe estar entre 4 y 255 caracteres")
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a cero")
    @Column(name = "precio", nullable = false)
    private double precio;

    @Min(value = 0, message = "La capacidad debe ser mayor o igual a cero")
    @Column(name = "capacidad", nullable = false)
    private int capacidad;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@JsonIgnore
    @OneToMany(mappedBy = "sectorAsiento", cascade = CascadeType.ALL)
    private List<Asiento> asientos;

    @PrePersist
    public void inicializarCapacidad() {
        this.capacidad = asientos.size();
    }

}
