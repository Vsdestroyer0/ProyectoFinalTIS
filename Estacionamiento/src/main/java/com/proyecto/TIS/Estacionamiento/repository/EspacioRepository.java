package com.proyecto.TIS.Estacionamiento.repository;

import com.proyecto.TIS.Estacionamiento.entity.Espacio;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EspacioRepository {
   @Select("SELECT * FROM espacioestacionamiento WHERE ocupado = false")
   List<Espacio> obtenerEspaciosDisponibles();

   @Update("UPDATE espacioestacionamiento SET ocupado = #{estado} WHERE idEspacio = #{idEspacio}")
   void actualizarEstadoEspacio(@Param("idEspacio")Integer idEspacio, @Param("estado")Boolean estado);
}