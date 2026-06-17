package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.TipoUsuarioEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TipoUsuarioRepository {

    // buscar tipo de usuario por id
    @Select("SELECT idTipo, nombre FROM tipo_usuario WHERE idTipo = #{id}")
    TipoUsuarioEntity findById(@Param("id") Integer id);
}
