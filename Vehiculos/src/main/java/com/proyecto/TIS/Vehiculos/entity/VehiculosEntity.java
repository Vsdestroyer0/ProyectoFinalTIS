package com.proyecto.TIS.Vehiculos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "vehiculo")
public class VehiculosEntity {
    @Id
    private Integer id;
}
