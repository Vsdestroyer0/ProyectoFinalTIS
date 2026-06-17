package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.MarcaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MarcaRepository {

    // buscar marca por id
    @Select("SELECT idMarca, marca FROM marca WHERE idMarca = #{id}")
    MarcaEntity findById(@Param("id") Integer id);
}
