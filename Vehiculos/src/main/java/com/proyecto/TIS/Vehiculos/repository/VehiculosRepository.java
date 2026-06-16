package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.VehiculosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculosRepository extends JpaRepository<VehiculosEntity, Integer> {

    //todos los vehiculos de un usuario especifico
    List<VehiculosEntity> findByIdUsuario(Integer idUsuario);

    
    //contar cuantos vehiculs activos tiene un usuarui
    long countByIdUsuarioAndEstatusTrue(Integer idUsuario);

    // validad si la placa ya existe
    boolean existsByPlaca(String placa);
}