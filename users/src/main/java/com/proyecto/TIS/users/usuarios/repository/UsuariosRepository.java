package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Integer> {
    
    UsuariosEntity findByUsername(String username);

    @Query("SELECT COUNT(u) > 0 FROM UsuariosEntity u WHERE u.username = :username OR u.email = :correo")
    boolean existeUsuarioPorCorreoOUsername(@Param("username") String username, @Param("correo") String correo);

    @Query("SELECT COUNT(u) > 0 FROM UsuariosEntity u WHERE u.email = :correo AND u.idUsuario != :idUsuario")
    boolean correoUsado(@Param("correo") String correo, @Param("idUsuario") int idUsuario);

    UsuariosEntity findByClaveUsuario(String claveUsuario);
}
