package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.ProgramaEducativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaEducativoRepository extends JpaRepository<ProgramaEducativoEntity, Integer> {
}
