package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.VehiculosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculosRepository extends JpaRepository<VehiculosEntity, Integer> {

    // traer todos los carros de un wey
    List<VehiculosEntity> findByIdUsuario(Integer idUsuario);

    // contar cuantos carros activos tiene para el limite
    long countByIdUsuarioAndEstatusTrue(Integer idUsuario);

    // ver si la placa ya la registraron
    boolean existsByPlaca(String placa);

    // ver si la placa ya la tiene otro carro (menos este)
    boolean existsByPlacaAndIdVehiculoNot(String placa, Integer idVehiculo);

    // buscar carro por placa
    java.util.Optional<VehiculosEntity> findByPlaca(String placa);
}