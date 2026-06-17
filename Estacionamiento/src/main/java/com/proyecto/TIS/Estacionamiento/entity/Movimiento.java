package com.proyecto.TIS.Estacionamiento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMovimiento")
    private Integer idMove;

    @Column(name = "idVehiculo")
    private Integer idVehiculo;

    @Column(name = "idEspacio")
    private Integer idSpace;

    @Column(name = "tarifaHora")
    private Double tarHora;

    @Column(name = "costoTotal")
    private Double costoT;

    @Column(name = "tiempoEntrada")
    private LocalDateTime tEntrada;

    @Column(name = "tiempoSalida")
    private LocalDateTime tSalida;

    @Column(name = "tiempoCreacion")
    private LocalDateTime tCreacion;

    @Column(name = "tiempoActualizacion")
    private LocalDateTime tActualizacion;

    @Column(name = "minutosEstacionado")
    private Integer minEstacionado;

    @Column(name = "horasCobradas")
    private Integer hCobradas;

    // los getters de toda la vida
    public Integer getIdMove() { return idMove; }
    public Integer getIdVehiculo() { return idVehiculo; }
    public Integer getIdSpace() { return idSpace; }
    public Double getTarHora() { return tarHora; }
    public Double getCostoT() { return costoT; }
    public LocalDateTime gettEntrada() { return tEntrada; }
    public LocalDateTime gettSalida() { return tSalida; }
    public LocalDateTime gettCreacion() { return tCreacion; }
    public LocalDateTime gettActualizacion() { return tActualizacion; }
    public Integer getMinEstacionado() { return minEstacionado; }
    public Integer gethCobradas() { return hCobradas; }

    // los setters de toda la vida
    public void setIdMove(Integer idMove) { this.idMove = idMove; }
    public void setIdVehiculo(Integer idVehiculo) { this.idVehiculo = idVehiculo; }
    public void setIdSpace(Integer idSpace) { this.idSpace = idSpace; }
    public void setTarHora(Double tarHora) { this.tarHora = tarHora; }
    public void setCostoT(Double costoT) { this.costoT = costoT; }
    public void settEntrada(LocalDateTime tEntrada) { this.tEntrada = tEntrada; }
    public void settSalida(LocalDateTime tSalida) { this.tSalida = tSalida; }
    public void settCreacion(LocalDateTime tCreacion) { this.tCreacion = tCreacion; }
    public void settActualizacion(LocalDateTime tActualizacion) { this.tActualizacion = tActualizacion; }
    public void setMinEstacionado(Integer minEstacionado) { this.minEstacionado = minEstacionado; }
    public void sethCobradas(Integer hCobradas) { this.hCobradas = hCobradas; }
}
