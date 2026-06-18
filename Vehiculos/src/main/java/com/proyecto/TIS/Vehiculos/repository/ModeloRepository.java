package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.ModeloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends JpaRepository<ModeloEntity, Integer> {
}
