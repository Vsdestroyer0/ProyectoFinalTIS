package com.proyecto.TIS.Estacionamiento.repository;

import com.proyecto.TIS.Estacionamiento.entity.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Integer> {

    // traer solo los cajones que esten libres y activos
    @Query("SELECT e FROM Espacio e WHERE e.ocupado = false")
    List<Espacio> obtenerEspaciosDisponibles();

    // buscar un cajon por su id
    @Query("SELECT e FROM Espacio e WHERE e.id = :idEspacio")
    Espacio obtenerPorId(@Param("idEspacio") Integer idEspacio);

    // poner el cajon como ocupado o libre
    @Modifying
    @Transactional
    @Query("UPDATE Espacio e SET e.ocupado = :estado WHERE e.id = :idEspacio")
    void actualizarEstadoEspacio(@Param("idEspacio") Integer idEspacio, @Param("estado") Boolean estado);
}