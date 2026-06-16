package com.proyecto.TIS.users.auth.dto;

public class SesionUsuarioDTO {
    private int idUsuario;
    private int idRol;
    private String rol;
    private String username;
    private String nombreCompleto;
    private int idTipoUsuario;
    private String tipoUsuario;
    private String token;

    public SesionUsuarioDTO(int idUsuario, int idRol, String rol, String username, String nombreCompleto, int idTipoUsuario, String tipoUsuario, String token) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.rol = rol;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.idTipoUsuario = idTipoUsuario;
        this.tipoUsuario = tipoUsuario;
        this.token = token;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }
    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
