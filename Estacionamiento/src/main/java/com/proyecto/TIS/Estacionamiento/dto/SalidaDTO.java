/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.TIS.Estacionamiento.dto;

import java.time.LocalDateTime;

/**
 *
 * @author LuisA
 */
public class SalidaDTO {

    private String claveU;
    private String placa;  
    private Double tarHora;
    private Integer idSpace;
    private java.time.LocalDateTime tSalida;
    private java.time.LocalDateTime tActualizacion; 
    private Integer minEstacionado;
    private Integer hCobradas;
    private Double costoT;
    
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

    public LocalDateTime gettSalida() {
        return tSalida;
    }

    public void settSalida(LocalDateTime tSalida) {
        this.tSalida = tSalida;
    }

    public LocalDateTime gettActualizacion() {
        return tActualizacion;
    }

    public void settActualizacion(LocalDateTime tActualizacion) {
        this.tActualizacion = tActualizacion;
    }

    public Integer getMinEstacionado() {
        return minEstacionado;
    }

    public void setMinEstacionado(Integer minEstacionado) {
        this.minEstacionado = minEstacionado;
    }

    public Integer gethCobradas() {
        return hCobradas;
    }

    public void sethCobradas(Integer hCobradas) {
        this.hCobradas = hCobradas;
    }

    public Double getCostoT() {
        return costoT;
    }

    public void setCostoT(Double costoT) {
        this.costoT = costoT;
    }
}