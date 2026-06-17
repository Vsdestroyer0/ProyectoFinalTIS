package com.proyecto.TIS.users.usuarios.repository;

import com.proyecto.TIS.users.usuarios.entity.UsuariosEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UsuariosRepository {

    // buscar usuario por nombre de usuario para el login
    @Select("SELECT idUsuario, nombre, apellidoPaterno, apellidoMaterno, claveUsuario, email, telefono, username, password, estatus, idRol, idTipoUsuario, idProgramaEducativo, tiempoCreacion, tempoActualizacion FROM usuario WHERE username = #{username}")
    @Results({
        @Result(property = "idUsuario", column = "idUsuario"),
        @Result(property = "apellidoPaterno", column = "apellidoPaterno"),
        @Result(property = "apellidoMaterno", column = "apellidoMaterno"),
        @Result(property = "claveUsuario", column = "claveUsuario"),
        @Result(property = "tiempoCreacion", column = "tiempoCreacion"),
        @Result(property = "tempoActualizacion", column = "tempoActualizacion"),
        @Result(property = "idRol", column = "idRol"),
        @Result(property = "idTipoUsuario", column = "idTipoUsuario"),
        @Result(property = "idProgramaEducativo", column = "idProgramaEducativo")
    })
    UsuariosEntity findByUsername(@Param("username") String username);

    // buscar por id
    @Select("SELECT idUsuario, nombre, apellidoPaterno, apellidoMaterno, claveUsuario, email, telefono, username, password, estatus, idRol, idTipoUsuario, idProgramaEducativo, tiempoCreacion, tempoActualizacion FROM usuario WHERE idUsuario = #{id}")
    @Results({
        @Result(property = "idUsuario", column = "idUsuario"),
        @Result(property = "apellidoPaterno", column = "apellidoPaterno"),
        @Result(property = "apellidoMaterno", column = "apellidoMaterno"),
        @Result(property = "claveUsuario", column = "claveUsuario"),
        @Result(property = "tiempoCreacion", column = "tiempoCreacion"),
        @Result(property = "tempoActualizacion", column = "tempoActualizacion"),
        @Result(property = "idRol", column = "idRol"),
        @Result(property = "idTipoUsuario", column = "idTipoUsuario"),
        @Result(property = "idProgramaEducativo", column = "idProgramaEducativo")
    })
    UsuariosEntity findById(@Param("id") Integer id);

    // ver si ya existe un usuario con ese correo o username
    @Select("SELECT COUNT(*) > 0 FROM usuario WHERE username = #{username} OR email = #{correo}")
    boolean existeUsuarioPorCorreoOUsername(@Param("username") String username, @Param("correo") String correo);

    // ver si el correo ya lo usa alguien mas
    @Select("SELECT COUNT(*) > 0 FROM usuario WHERE email = #{correo} AND idUsuario != #{idUsuario}")
    boolean correoUsado(@Param("correo") String correo, @Param("idUsuario") int idUsuario);

    // checar si existe el usuario por id
    @Select("SELECT COUNT(*) > 0 FROM usuario WHERE idUsuario = #{id}")
    boolean existsById(@Param("id") Integer id);

    // buscar por clave de usuario
    @Select("SELECT idUsuario, nombre, apellidoPaterno, apellidoMaterno, claveUsuario, email, telefono, username, password, estatus, idRol, idTipoUsuario, idProgramaEducativo, tiempoCreacion, tempoActualizacion FROM usuario WHERE claveUsuario = #{claveUsuario}")
    @Results({
        @Result(property = "idUsuario", column = "idUsuario"),
        @Result(property = "apellidoPaterno", column = "apellidoPaterno"),
        @Result(property = "apellidoMaterno", column = "apellidoMaterno"),
        @Result(property = "claveUsuario", column = "claveUsuario"),
        @Result(property = "tiempoCreacion", column = "tiempoCreacion"),
        @Result(property = "tempoActualizacion", column = "tempoActualizacion"),
        @Result(property = "idRol", column = "idRol"),
        @Result(property = "idTipoUsuario", column = "idTipoUsuario"),
        @Result(property = "idProgramaEducativo", column = "idProgramaEducativo")
    })
    UsuariosEntity findByClaveUsuario(@Param("claveUsuario") String claveUsuario);

    // guardar usuario nuevo
    @Options(useGeneratedKeys = true, keyProperty = "idUsuario", keyColumn = "idUsuario")
    @Insert("INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, claveUsuario, email, telefono, username, password, estatus, idRol, idTipoUsuario, idProgramaEducativo, tiempoCreacion) VALUES (#{nombre}, #{apellidoPaterno}, #{apellidoMaterno}, #{claveUsuario}, #{email}, #{telefono}, #{username}, #{password}, #{estatus}, #{idRol}, #{idTipoUsuario}, #{idProgramaEducativo}, #{tiempoCreacion})")
    void save(UsuariosEntity usuario);

    // actualizar datos del usuario
    @Update("UPDATE usuario SET nombre=#{nombre}, apellidoPaterno=#{apellidoPaterno}, apellidoMaterno=#{apellidoMaterno}, email=#{email}, telefono=#{telefono}, idRol=#{idRol}, idTipoUsuario=#{idTipoUsuario}, idProgramaEducativo=#{idProgramaEducativo}, estatus=#{estatus}, tempoActualizacion=#{tempoActualizacion} WHERE idUsuario=#{idUsuario}")
    void update(UsuariosEntity usuario);
}
