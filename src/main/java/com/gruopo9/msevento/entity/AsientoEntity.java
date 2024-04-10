package com.gruopo9.msevento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import com.gruopo9.msevento.entity.common.Audit;

@Entity

@Table(name = "asientos")
@Getter
@Setter
public class AsientoEntity extends Audit implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "El numero de asiento debe ser mayor  a cero")
    @Column(name = "numero_asiento", nullable = false)
    private int numeroAsiento;

    @Column(name = "estado")
    private boolean estado;

    public AsientoEntity(String userCreate, Date dateCreate, String userModif, Date dateModif, String userDelet, Date dateDelet) {
        super(userCreate, dateCreate, userModif, dateModif, userDelet, dateDelet);
    }
    public AsientoEntity() {

    }

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sector_asiento_id", nullable = false)
//   private SectorAsientoEntity sectorAsiento;

    @PrePersist
    private void inicializarPorDefecto(){
        this.estado=true;
        setDateCreate(new Date());
    }
}