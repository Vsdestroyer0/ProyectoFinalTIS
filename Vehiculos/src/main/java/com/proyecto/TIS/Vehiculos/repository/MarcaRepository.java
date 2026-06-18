package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.MarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaEntity, Integer> {
}
