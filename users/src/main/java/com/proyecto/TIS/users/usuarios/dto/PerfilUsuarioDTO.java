package com.proyecto.TIS.users.usuarios.dto;

import java.time.LocalDateTime;

public class PerfilUsuarioDTO {
    private String rol;
    private String nombreCompleto;
    private String tipoUsuario;
    private String programaEducativo;
    private String username;
    private String correo;
    private String telefono;
    private boolean status;
    private String claveUsuario;
    private LocalDateTime tiempoCreacion;
    private LocalDateTime tiempoActualizacion;

    public PerfilUsuarioDTO() {}

    public PerfilUsuarioDTO(String rol, String nombreCompleto, String tipoUsuario, String programaEducativo, 
                            String username, String correo, String telefono, boolean status, String claveUsuario, 
                            LocalDateTime tiempoCreacion, LocalDateTime tiempoActualizacion) {
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.tipoUsuario = tipoUsuario;
        this.programaEducativo = programaEducativo;
        this.username = username;
        this.correo = correo;
        this.telefono = telefono;
        this.status = status;
        this.claveUsuario = claveUsuario;
        this.tiempoCreacion = tiempoCreacion;
        this.tiempoActualizacion = tiempoActualizacion;
    }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getProgramaEducativo() { return programaEducativo; }
    public void setProgramaEducativo(String programaEducativo) { this.programaEducativo = programaEducativo; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getClaveUsuario() { return claveUsuario; }
    public void setClaveUsuario(String claveUsuario) { this.claveUsuario = claveUsuario; }

    public LocalDateTime getTiempoCreacion() { return tiempoCreacion; }
    public void setTiempoCreacion(LocalDateTime tiempoCreacion) { this.tiempoCreacion = tiempoCreacion; }

    public LocalDateTime getTiempoActualizacion() { return tiempoActualizacion; }
    public void setTiempoActualizacion(LocalDateTime tiempoActualizacion) { this.tiempoActualizacion = tiempoActualizacion; }
}
