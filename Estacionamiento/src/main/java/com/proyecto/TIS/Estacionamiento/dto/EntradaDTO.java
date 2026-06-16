package com.proyecto.TIS.Estacionamiento.dto;

import java.time.LocalDateTime;

public class EntradaDTO {
    
    private String claveU;
    private String placa;  
    private Double tarHora;
    private Integer idSpace;
    private java.time.LocalDateTime tEntrada;
    private java.time.LocalDateTime tCreacion;

    public String getClaveU() {
        return claveU;
    }

    public void setClaveU(String claveU) {
        this.claveU = claveU;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Double getTarHora() {
        return tarHora;
    }

    public void setTarHora(Double tarHora) {
        this.tarHora = tarHora;
    }

    public Integer getIdSpace() {
        return idSpace;
    }

    public void setIdSpace(Integer idSpace) {
        this.idSpace = idSpace;
    }

    public LocalDateTime gettEntrada() {
        return tEntrada;
    }

    public void settEntrada(LocalDateTime tEntrada) {
        this.tEntrada = tEntrada;
    }

    public LocalDateTime gettCreacion() {
        return tCreacion;
    }

    public void settCreacion(LocalDateTime tCreacion) {
        this.tCreacion = tCreacion;
    }

}