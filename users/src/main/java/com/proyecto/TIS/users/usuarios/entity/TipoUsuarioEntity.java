package com.proyecto.TIS.users.usuarios.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_usuario")
public class TipoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipo;

    @Column(nullable = false, length = 50)
    private String nombre;

    public Integer getIdTipo() {
        return idTipo;
    }
    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
