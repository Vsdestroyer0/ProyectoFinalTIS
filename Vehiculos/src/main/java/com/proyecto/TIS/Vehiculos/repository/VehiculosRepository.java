package com.proyecto.TIS.Vehiculos.repository;

import com.proyecto.TIS.Vehiculos.entity.VehiculosEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Optional;

@Mapper
public interface VehiculosRepository {

    // traer todos los carros de un wey
    @Select("SELECT idVehiculo, idUsuario, claveVehiculo, idModelo, placa, color, anio, descripcion, estatus FROM vehiculo WHERE idUsuario = #{idUsuario}")
    List<VehiculosEntity> findByIdUsuario(@Param("idUsuario") Integer idUsuario);

    // buscar un carro por su id
    @Select("SELECT idVehiculo, idUsuario, claveVehiculo, idModelo, placa, color, anio, descripcion, estatus FROM vehiculo WHERE idVehiculo = #{id}")
    VehiculosEntity findById(@Param("id") Integer id);

    // contar cuantos carros activos tiene para el limite
    @Select("SELECT COUNT(*) FROM vehiculo WHERE idUsuario = #{idUsuario} AND estatus = true")
    long countByIdUsuarioAndEstatusTrue(@Param("idUsuario") Integer idUsuario);

    // ver si la placa ya la registraron
    @Select("SELECT COUNT(*) > 0 FROM vehiculo WHERE placa = #{placa}")
    boolean existsByPlaca(@Param("placa") String placa);

    // ver si la placa ya la tiene otro carro (menos este)
    @Select("SELECT COUNT(*) > 0 FROM vehiculo WHERE placa = #{placa} AND idVehiculo != #{idVehiculo}")
    boolean existsByPlacaAndIdVehiculoNot(@Param("placa") String placa, @Param("idVehiculo") Integer idVehiculo);

    // buscar carro por placa
    @Select("SELECT idVehiculo, idUsuario, claveVehiculo, idModelo, placa, color, anio, descripcion, estatus FROM vehiculo WHERE placa = #{placa}")
    VehiculosEntity findByPlaca(@Param("placa") String placa);

    // traer todas las claves para generar la siguiente
    @Select("SELECT claveVehiculo FROM vehiculo")
    List<String> findAllClaves();

    // guardar carro nuevo
    @Options(useGeneratedKeys = true, keyProperty = "idVehiculo", keyColumn = "idVehiculo")
    @Insert("INSERT INTO vehiculo (idUsuario, claveVehiculo, idModelo, placa, color, anio, descripcion, estatus) VALUES (#{idUsuario}, #{claveVehiculo}, #{idModelo}, #{placa}, #{color}, #{anio}, #{descripcion}, #{estatus})")
    void save(VehiculosEntity vehiculo);

    // actualizar datos del carro
    @Update("UPDATE vehiculo SET idUsuario=#{idUsuario}, idModelo=#{idModelo}, placa=#{placa}, color=#{color}, anio=#{anio}, descripcion=#{descripcion}, estatus=#{estatus} WHERE idVehiculo=#{idVehiculo}")
    void update(VehiculosEntity vehiculo);
}