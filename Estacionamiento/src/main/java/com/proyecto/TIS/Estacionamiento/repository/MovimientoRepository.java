package com.proyecto.TIS.Estacionamiento.repository;

import com.proyecto.TIS.Estacionamiento.entity.Movimiento;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MovimientoRepository {

    // checar si el carro ya esta en el estacionamiento para que no haga trampa
    @Select("SELECT COUNT(*) FROM movimiento WHERE idVehiculo = #{idVehiculo} AND tiempoSalida IS NULL")
    Integer contarMovimientosActivosPorVehiculo(@Param("idVehiculo") Integer idVehiculo);

    // buscar cuando entro para poder cobrarle
    @Select("SELECT idMovimiento AS idMove, idVehiculo, idEspacio AS idSpace, tarifaHora AS tarHora, tiempoEntrada AS tEntrada, tiempoSalida AS tSalida, tiempoCreacion AS tCreacion, tiempoActualizacion AS tActualizacion, minutosEstacionado AS minEstacionado, horasCobradas AS hCobradas, costoTotal AS costoT FROM movimiento WHERE idVehiculo = #{idVehiculo} AND tiempoSalida IS NULL")
    Movimiento obtenerMovimientoActivoPorVehiculo(@Param("idVehiculo") Integer idVehiculo);

    // meter el registro de entrada
    @Options(useGeneratedKeys = true, keyProperty = "idMove", keyColumn = "idMovimiento")
    @Insert("INSERT INTO movimiento (idVehiculo, idEspacio, tarifaHora, tiempoEntrada, tiempoCreacion) VALUES (#{idVehiculo}, #{idSpace}, #{tarHora}, #{tEntrada}, #{tCreacion})")
    void registrarEntrada(Movimiento movimiento);

    // actualizar los datos de salida
    @Update("UPDATE movimiento SET tiempoSalida = #{tSalida}, tiempoActualizacion = #{tActualizacion}, minutosEstacionado = #{minEstacionado}, horasCobradas = #{hCobradas}, costoTotal = #{costoT} WHERE idMovimiento = #{idMove}")
    void registrarSalida(Movimiento movimiento);
}