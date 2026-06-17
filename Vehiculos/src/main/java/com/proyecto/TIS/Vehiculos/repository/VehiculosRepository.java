package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.VehiculosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculosRepository extends JpaRepository<VehiculosEntity, Integer> {

    // Todos los vehículos de un usuario específico
    List<VehiculosEntity> findByIdUsuario(Integer idUsuario);

    // Contar cuántos vehículos activos tiene un usuario
    long countByIdUsuarioAndEstatusTrue(Integer idUsuario);

    // Validar si la placa ya existe
    boolean existsByPlaca(String placa);

    // Validar placa al editar (excluyendo el propio vehículo)
    boolean existsByPlacaAndIdVehiculoNot(String placa, Integer idVehiculo);

    // Buscar vehículo por placa
    java.util.Optional<VehiculosEntity> findByPlaca(String placa);
}