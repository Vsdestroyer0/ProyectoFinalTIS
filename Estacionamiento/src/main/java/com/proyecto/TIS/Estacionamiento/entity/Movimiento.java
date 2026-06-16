/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.TIS.Estacionamiento.entity;

/**
 *
 * @author LuisA
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity 
@Table(name = "movimiento")
public class Movimiento {
    
    private String claveU;
    private String placa;
    private Integer idSpace;
    
    private Double tarHora;
    private Double costoT;
    
    private java.time.LocalDateTime tEntrada;
    private java.time.LocalDateTime tSalida;
    private java.time.LocalDateTime tCreacion;
    private java.time.LocalDateTime tActualizacion;
    private Integer minEstacionado;
    private Integer hCobradas;
    
    @Id
    private Integer idMove;

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

    public Integer getIdSpace() {
        return idSpace;
    }

    public void setIdSpace(Integer idSpace) {
        this.idSpace = idSpace;
    }

    public Double getTarHora() {
        return tarHora;
    }

    public void setTarHora(Double tarHora) {
        this.tarHora = tarHora;
    }

    public Double getCostoT() {
        return costoT;
    }

    public void setCostoT(Double costoT) {
        this.costoT = costoT;
    }

    public LocalDateTime gettEntrada() {
        return tEntrada;
    }

    public void settEntrada(LocalDateTime tEntrada) {
        this.tEntrada = tEntrada;
    }

    public LocalDateTime gettSalida() {
        return tSalida;
    }

    public void settSalida(LocalDateTime tSalida) {
        this.tSalida = tSalida;
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

    public Integer getIdMove() {
        return idMove;
    }

    public void setIdMove(Integer idMove) {
        this.idMove = idMove;
    }
    public LocalDateTime gettCreacion() {
        return tCreacion;
    }

    public void settCreacion(LocalDateTime tCreacion) {
        this.tCreacion = tCreacion;
    }

    public LocalDateTime gettActualizacion() {
        return tActualizacion;
    }

    public void settActualizacion(LocalDateTime tActualizacion) {
        this.tActualizacion = tActualizacion;
    }
}
