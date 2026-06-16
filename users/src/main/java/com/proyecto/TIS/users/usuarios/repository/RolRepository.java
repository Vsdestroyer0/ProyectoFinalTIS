package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Integer> {
}
