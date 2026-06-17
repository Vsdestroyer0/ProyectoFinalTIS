package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.ProgramaEducativoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProgramaEducativoRepository {

    // buscar programa educativo por id
    @Select("SELECT idPrograma, nombre FROM programa_educativo WHERE idPrograma = #{id}")
    ProgramaEducativoEntity findById(@Param("id") Integer id);
}
