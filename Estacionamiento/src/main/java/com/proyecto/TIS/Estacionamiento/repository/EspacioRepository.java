package com.proyecto.TIS.Estacionamiento.repository;

import com.proyecto.TIS.Estacionamiento.entity.Espacio;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EspacioRepository {
   @Select("SELECT idEspacio AS id, ocupado FROM espacioestacionamiento WHERE ocupado = false AND estatus = true")
   List<Espacio> obtenerEspaciosDisponibles();

   @Select("SELECT idEspacio AS id, ocupado FROM espacioestacionamiento WHERE idEspacio = #{idEspacio}")
   Espacio obtenerPorId(@Param("idEspacio") Integer idEspacio);

   @Update("UPDATE espacioestacionamiento SET ocupado = #{estado} WHERE idEspacio = #{idEspacio}")
   void actualizarEstadoEspacio(@Param("idEspacio") Integer idEspacio, @Param("estado") Boolean estado);
}