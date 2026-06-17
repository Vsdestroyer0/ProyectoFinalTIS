package com.proyecto.TIS.Estacionamiento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "espacioestacionamiento")
public class Espacio {

    @Id
    @Column(name = "idEspacio")
    private Integer id;

    @Column(name = "ocupado")
    private Boolean ocupado;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Boolean getOcupado() { return ocupado; }
    public void setOcupado(Boolean ocupado) { this.ocupado = ocupado; }
}
