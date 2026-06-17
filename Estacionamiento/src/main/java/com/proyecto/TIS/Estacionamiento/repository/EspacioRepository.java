package com.proyecto.TIS.Estacionamiento.repository;

import com.proyecto.TIS.Estacionamiento.entity.Espacio;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EspacioRepository {

    // traer solo los cajones que esten libres y activos
    @Select("SELECT idEspacio AS id, ocupado FROM espacioestacionamiento WHERE ocupado = false AND estatus = true")
    List<Espacio> obtenerEspaciosDisponibles();

    // buscar un cajon por su id
    @Select("SELECT idEspacio AS id, ocupado FROM espacioestacionamiento WHERE idEspacio = #{idEspacio}")
    Espacio obtenerPorId(@Param("idEspacio") Integer idEspacio);

    // poner el cajon como ocupado o libre
    @Update("UPDATE espacioestacionamiento SET ocupado = #{estado} WHERE idEspacio = #{idEspacio}")
    void actualizarEstadoEspacio(@Param("idEspacio") Integer idEspacio, @Param("estado") Boolean estado);
}