package com.proyecto.TIS.users.usuarios.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class UsuariosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(length = 50)
    private String apellidoMaterno;

    @Column(nullable = false, length = 10)
    private String claveUsuario;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 10)
    private String telefono;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean estatus;

    @Column(nullable = false)
    private Integer idRol;

    @Column(nullable = false)
    private Integer idTipoUsuario;

    @Column(nullable = false)
    private Integer idProgramaEducativo;

    private LocalDateTime tiempoCreacion;
    private LocalDateTime tempoActualizacion;

    // Getters
    public Integer getIdUsuario() {
        return idUsuario;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    public String getClaveUsuario() {
        return claveUsuario;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean isEstatus() {
        return estatus;
    }
    public Integer getIdRol() {
        return idRol;
    }
    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }
    public Integer getIdProgramaEducativo() {
        return idProgramaEducativo;
    }
    public LocalDateTime getTiempoCreacion() {
        return tiempoCreacion;
    }
    public LocalDateTime getTempoActualizacion() {
        return tempoActualizacion;
    }

    // Setters
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }
    public void setIdProgramaEducativo(Integer idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }
    public void setTiempoCreacion(LocalDateTime tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }
    public void setTempoActualizacion(LocalDateTime tempoActualizacion) {
        this.tempoActualizacion = tempoActualizacion;
    }
}
