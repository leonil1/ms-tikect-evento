package com.gruopo9.msevento.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gruopo9.msevento.entity.common.Audit;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "sector_asientos")
@Getter
@Setter
public class SectorAsientoEntity extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede ser vacío")
    @Size(min = 4, max = 255, message = "El tamaño de nombre debe estar entre 4 y 255 caracteres")
    @Column(name = "nombre_sector", nullable = false, length = 255)
    private String nombreSector;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a cero")
    @Column(name = "precio", nullable = false)
    private double precio;

    @Min(value = 0, message = "La capacidad debe ser mayor o igual a cero")
    @Column(name = "capacidad_sector", nullable = false)
    private int capacidadSector;


    @NotEmpty(message = "La descripcion del sector no puede ser vacío")
    @Size(min = 4, max = 60, message = "El tamaño de dscripcion de asiento debe estar entre 4 y 255 caracteres")
    @Column(name = "descripcion_asientos", nullable = false, length = 255)
    private String descripcionAsientos;

    public SectorAsientoEntity() {

    }
    public SectorAsientoEntity(String userCreate, Date dateCreate, String userModif, Date dateModif, String userDelet, Date dateDelet) {
        super(userCreate, dateCreate, userModif, dateModif, userDelet, dateDelet);
    }



    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_asiento_id")
    private List<AsientoEntity> asientos;

    @PrePersist
    public void inicializarPorDefecto() {
        setDateCreate(new Date());
        this.capacidadSector = asientos.size();
    }

}
