package com.proyecto.TIS.Estacionamiento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "espacioestacionamiento")
public class EstacionamientoEntity {
    @Id
    private Integer id;
}
