package com.proyecto.TIS.Estacionamiento.repository;

import com.proyecto.TIS.Estacionamiento.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {

    // checar si el carro ya esta en el estacionamiento para que no haga trampa
    @Query("SELECT COUNT(m) FROM Movimiento m WHERE m.idVehiculo = :idVehiculo AND m.tSalida IS NULL")
    Integer contarMovimientosActivosPorVehiculo(@Param("idVehiculo") Integer idVehiculo);

    // buscar cuando entro para poder cobrarle
    @Query("SELECT m FROM Movimiento m WHERE m.idVehiculo = :idVehiculo AND m.tSalida IS NULL")
    Movimiento obtenerMovimientoActivoPorVehiculo(@Param("idVehiculo") Integer idVehiculo);
}