package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.ModeloEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ModeloRepository {

    // buscar modelo por id
    @Select("SELECT idModelo, idMarca, modelo FROM modelo WHERE idModelo = #{id}")
    ModeloEntity findById(@Param("id") Integer id);
}
