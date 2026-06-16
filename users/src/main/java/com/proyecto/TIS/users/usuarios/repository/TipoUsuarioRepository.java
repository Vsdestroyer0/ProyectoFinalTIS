package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Integer> {
}
