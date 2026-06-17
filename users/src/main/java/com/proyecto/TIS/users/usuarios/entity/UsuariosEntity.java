package com.proyecto.TIS.users.usuarios.entity;

import java.time.LocalDateTime;

public class UsuariosEntity {

    private Integer idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String claveUsuario;
    private String email;
    private String telefono;
    private String username;
    private String password;
    private boolean estatus;
    private Integer idRol;
    private Integer idTipoUsuario;
    private Integer idProgramaEducativo;
    private LocalDateTime tiempoCreacion;
    private LocalDateTime tempoActualizacion;

    // los getters de toda la vida
    public Integer getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public String getClaveUsuario() { return claveUsuario; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isEstatus() { return estatus; }
    public Integer getIdRol() { return idRol; }
    public Integer getIdTipoUsuario() { return idTipoUsuario; }
    public Integer getIdProgramaEducativo() { return idProgramaEducativo; }
    public LocalDateTime getTiempoCreacion() { return tiempoCreacion; }
    public LocalDateTime getTempoActualizacion() { return tempoActualizacion; }

    // los setters de toda la vida
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    public void setClaveUsuario(String claveUsuario) { this.claveUsuario = claveUsuario; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEstatus(boolean estatus) { this.estatus = estatus; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public void setIdTipoUsuario(Integer idTipoUsuario) { this.idTipoUsuario = idTipoUsuario; }
    public void setIdProgramaEducativo(Integer idProgramaEducativo) { this.idProgramaEducativo = idProgramaEducativo; }
    public void setTiempoCreacion(LocalDateTime tiempoCreacion) { this.tiempoCreacion = tiempoCreacion; }
    public void setTempoActualizacion(LocalDateTime tempoActualizacion) { this.tempoActualizacion = tempoActualizacion; }
}
