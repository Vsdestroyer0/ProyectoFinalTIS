package com.proyecto.TIS.users.usuarios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "programa_educativo")
public class ProgramaEducativoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrograma;

    @Column(nullable = false, length = 150)
    private String nombre;

    public Integer getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Integer idPrograma) { this.idPrograma = idPrograma; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
