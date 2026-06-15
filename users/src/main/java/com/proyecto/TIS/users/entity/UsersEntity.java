package com.proyecto.TIS.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "usuario")
public class UsersEntity {
    @Id
    private Integer idUsuario;
}
