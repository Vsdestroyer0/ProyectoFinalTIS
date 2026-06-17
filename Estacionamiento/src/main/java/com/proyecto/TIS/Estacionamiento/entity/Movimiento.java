package com.proyecto.TIS.Estacionamiento.entity;

import java.time.LocalDateTime;

public class Movimiento {

    private Integer idMove;
    private Integer idVehiculo;
    private Integer idSpace;
    private Double tarHora;
    private Double costoT;
    private LocalDateTime tEntrada;
    private LocalDateTime tSalida;
    private LocalDateTime tCreacion;
    private LocalDateTime tActualizacion;
    private Integer minEstacionado;
    private Integer hCobradas;

    public Integer getIdMove() { return idMove; }
    public void setIdMove(Integer idMove) { this.idMove = idMove; }
    public Integer getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Integer idVehiculo) { this.idVehiculo = idVehiculo; }
    public Integer getIdSpace() { return idSpace; }
    public void setIdSpace(Integer idSpace) { this.idSpace = idSpace; }
    public Double getTarHora() { return tarHora; }
    public void setTarHora(Double tarHora) { this.tarHora = tarHora; }
    public Double getCostoT() { return costoT; }
    public void setCostoT(Double costoT) { this.costoT = costoT; }
    public LocalDateTime gettEntrada() { return tEntrada; }
    public void settEntrada(LocalDateTime tEntrada) { this.tEntrada = tEntrada; }
    public LocalDateTime gettSalida() { return tSalida; }
    public void settSalida(LocalDateTime tSalida) { this.tSalida = tSalida; }
    public LocalDateTime gettCreacion() { return tCreacion; }
    public void settCreacion(LocalDateTime tCreacion) { this.tCreacion = tCreacion; }
    public LocalDateTime gettActualizacion() { return tActualizacion; }
    public void settActualizacion(LocalDateTime tActualizacion) { this.tActualizacion = tActualizacion; }
    public Integer getMinEstacionado() { return minEstacionado; }
    public void setMinEstacionado(Integer minEstacionado) { this.minEstacionado = minEstacionado; }
    public Integer gethCobradas() { return hCobradas; }
    public void sethCobradas(Integer hCobradas) { this.hCobradas = hCobradas; }
}
