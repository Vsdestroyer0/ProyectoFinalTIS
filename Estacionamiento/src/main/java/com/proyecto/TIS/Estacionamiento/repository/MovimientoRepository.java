/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyecto.TIS.Estacionamiento.repository;

/**
 *
 * @author LuisA
 */
import com.proyecto.TIS.Estacionamiento.entity.Movimiento;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MovimientoRepository {
     //Para saber si un carro ya esta adentro
    @Select("SELECT COUNT(*) FROM movimiento WHERE idVehiculo = #{idVehiculo} AND tiempoSalida IS NULL")
    Integer contarMovimientosActivosPorVehiculo(@Param("idVehiculo")Integer idVehiculo); 
    
    @Insert("INSERT INTO movimiento (idVehiculo, idEspacio, tarifaHora, tiempoEntrada, tiempoCreacion) VALUES (#{idVehiculo}, #{idSpace}, #{tarHora}, #{tEntrada}, #{tCreacion})")
    void registrarEntrada(Movimiento movimiento);
    
    @Update("UPDATE movimiento SET tiempoSalida = #{tSalida}, tiempoActualizacion = #{tActualizacion}, minutosEstacionado = #{minEstacionado}, horasCobradas = #{hCobradas}, costoTotal = #{costoT} WHERE idMovimiento = #{idMove}")
    void registrarSalida(Movimiento movimiento);
}