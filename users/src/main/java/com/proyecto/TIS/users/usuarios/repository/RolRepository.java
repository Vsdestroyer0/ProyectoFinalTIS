package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.RolEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RolRepository {

    // buscar rol por id
    @Select("SELECT idRol, nombre FROM rol WHERE idRol = #{id}")
    RolEntity findById(@Param("id") Integer id);
}
