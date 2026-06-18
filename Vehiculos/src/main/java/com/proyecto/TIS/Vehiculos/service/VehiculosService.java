package com.proyecto.TIS.Vehiculos.service;

import com.proyecto.TIS.Vehiculos.dto.VehiculoRequestDTO;
import com.proyecto.TIS.Vehiculos.dto.VehiculoResponseDTO;
import com.proyecto.TIS.Vehiculos.entity.MarcaEntity;
import com.proyecto.TIS.Vehiculos.entity.ModeloEntity;
import com.proyecto.TIS.Vehiculos.entity.VehiculosEntity;
import com.proyecto.TIS.Vehiculos.repository.MarcaRepository;
import com.proyecto.TIS.Vehiculos.repository.ModeloRepository;
import com.proyecto.TIS.Vehiculos.repository.VehiculosRepository;
import com.proyecto.TIS.Vehiculos.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculosService {

    @Autowired
    private VehiculosRepository repository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private JwtUtils jwtUtils;

    // Construye el DTO de respuesta con datos de modelo y marca
    private VehiculoResponseDTO buildResponse(VehiculosEntity v) {
        String nombreModelo = "Desconocido";
        Integer idMarca = null;
        String nombreMarca = "Desconocido";

        ModeloEntity modelo = modeloRepository.findById(v.getIdModelo()).orElse(null);
        if (modelo != null) {
            nombreModelo = modelo.getModelo();
            idMarca = modelo.getIdMarca();
            MarcaEntity marca = marcaRepository.findById(idMarca).orElse(null);
            if (marca != null) {
                nombreMarca = marca.getMarca();
            }
        }

        return new VehiculoResponseDTO(
            v.getIdUsuario(),
            v.getIdVehiculo(),
            v.getIdModelo(),
            nombreModelo,
            idMarca,
            nombreMarca,
            v.getPlaca(),
            v.getColor(),
            v.getAnio(),
            v.getDescripcion(),
            v.getEstatus()
        );
    }

    // generamos la clave del carro automatica tipo V-001 o asi
    private String generarClaveVehiculo() {
        List<String> claves = repository.findAll().stream()
                .map(VehiculosEntity::getClaveVehiculo)
                .collect(java.util.stream.Collectors.toList());
        String clave;
        do {
            int numero = (int)(Math.random() * 900) + 100;
            clave = "V-" + numero;
        } while (claves.contains(clave));
        return clave;
    }

    // buscar los carros que tiene guardados un usuario
    public List<VehiculoResponseDTO> buscarPorUsuario(Integer idUsuario, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        if (idUsuario == null) {
            throw new RuntimeException("El idUsuario es obligatorio");
        }

        Integer idUsuarioToken = jwtUtils.getIdUsuarioFromJwtToken(token);
        Integer idRolToken = jwtUtils.getIdRolFromJwtToken(token);

        if (idRolToken != 1 && !idUsuario.equals(idUsuarioToken)) {
            throw new RuntimeException("Acceso denegado: No tienes permiso para ver los vehículos de otro usuario");
        }

        List<VehiculosEntity> vehiculos = repository.findByIdUsuario(idUsuario);
        List<VehiculoResponseDTO> resultado = new ArrayList<>();
        for (VehiculosEntity v : vehiculos) {
            resultado.add(buildResponse(v));
        }
        return resultado;
    }

    // meter un carro nuevo a la base de datos
    public String registrar(VehiculoRequestDTO request, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        // validar que no dejen cosas vacias
        if (request.getIdUsuario() == null) {
            throw new RuntimeException("El idUsuario es obligatorio");
        }
        if (request.getIdModelo() == null) {
            throw new RuntimeException("El idModelo es obligatorio");
        }
        if (request.getPlaca() == null || request.getPlaca().trim().isEmpty()) {
            throw new RuntimeException("La placa es obligatoria");
        }
        if (request.getColor() == null || request.getColor().trim().isEmpty()) {
            throw new RuntimeException("El color es obligatorio");
        }
        if (request.getAnio() == null) {
            throw new RuntimeException("El año es obligatorio");
        }
        if (request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción es obligatoria");
        }

        // que los textos no esten mas largos de lo que aguanta la base de datos
        if (request.getPlaca().length() > 7) {
            throw new RuntimeException("La placa no puede exceder los 7 caracteres");
        }
        if (request.getColor().length() > 20) {
            throw new RuntimeException("El color no puede exceder los 20 caracteres");
        }
        if (request.getDescripcion().length() > 255) {
            throw new RuntimeException("La descripción no puede exceder los 255 caracteres");
        }

        // seguridad bola: que el usuario de la sesion sea el que registra el carro
        Integer idUsuarioToken = jwtUtils.getIdUsuarioFromJwtToken(token);
        Integer idRolToken = jwtUtils.getIdRolFromJwtToken(token);

        if (idRolToken != 1 && !request.getIdUsuario().equals(idUsuarioToken)) {
            throw new RuntimeException("Acceso denegado: No puedes registrar vehículos para otro usuario");
        }

        // no dejar registrar mas de 4 carros activos por wey
        long activos = repository.countByIdUsuarioAndEstatusTrue(request.getIdUsuario());
        if (activos >= 4) {
            throw new RuntimeException("El usuario ya tiene el límite de 4 vehículos activos");
        }

        // que no repitan placas pq no se puede
        if (repository.existsByPlaca(request.getPlaca())) {
            throw new RuntimeException("Ya existe un vehículo registrado con la placa: " + request.getPlaca());
        }

        VehiculosEntity nuevo = new VehiculosEntity();
        nuevo.setIdUsuario(request.getIdUsuario());
        nuevo.setIdModelo(request.getIdModelo());
        nuevo.setPlaca(request.getPlaca().toUpperCase());
        nuevo.setColor(request.getColor());
        nuevo.setAnio(request.getAnio());
        nuevo.setDescripcion(request.getDescripcion());
        nuevo.setEstatus(true); // que inicie prendido/activo
        nuevo.setClaveVehiculo(generarClaveVehiculo());

        repository.save(nuevo);
        return "Vehículo registrado correctamente con clave: " + nuevo.getClaveVehiculo();
    }

    // para cuando quieran editar el carro
    public String editar(Integer idVehiculo, VehiculoRequestDTO request, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        // validar que manden todo lo necesario
        if (request.getIdUsuario() == null) {
            throw new RuntimeException("El idUsuario es obligatorio");
        }
        if (request.getIdModelo() == null) {
            throw new RuntimeException("El idModelo es obligatorio");
        }
        if (request.getPlaca() == null || request.getPlaca().trim().isEmpty()) {
            throw new RuntimeException("La placa es obligatoria");
        }
        if (request.getColor() == null || request.getColor().trim().isEmpty()) {
            throw new RuntimeException("El color es obligatorio");
        }
        if (request.getAnio() == null) {
            throw new RuntimeException("El año es obligatorio");
        }
        if (request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción es obligatoria");
        }

        // validar longitud de los textos pq sino truena la bd
        if (request.getPlaca().length() > 7) {
            throw new RuntimeException("La placa no puede exceder los 7 caracteres");
        }
        if (request.getColor().length() > 20) {
            throw new RuntimeException("El color no puede exceder los 20 caracteres");
        }
        if (request.getDescripcion().length() > 255) {
            throw new RuntimeException("La descripción no puede exceder los 255 caracteres");
        }

        VehiculosEntity vehiculo = repository.findById(idVehiculo).orElse(null);
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado");
        }

        // validar que el carro si sea suyo antes de dejarlo editar
        Integer idUsuarioToken = jwtUtils.getIdUsuarioFromJwtToken(token);
        Integer idRolToken = jwtUtils.getIdRolFromJwtToken(token);

        if (idRolToken != 1 && !vehiculo.getIdUsuario().equals(idUsuarioToken)) {
            throw new RuntimeException("Acceso denegado: solo puedes editar tus propios vehículos");
        }

        if (idRolToken != 1 && !request.getIdUsuario().equals(idUsuarioToken)) {
            throw new RuntimeException("Acceso denegado: no puedes transferir el vehículo a otro usuario");
        }

        // placa repetida no, pero sin contar el que estamos editando ahorita
        if (repository.existsByPlacaAndIdVehiculoNot(request.getPlaca(), idVehiculo)) {
            throw new RuntimeException("Ya existe otro vehículo con la placa: " + request.getPlaca());
        }

        vehiculo.setIdUsuario(request.getIdUsuario());
        vehiculo.setIdModelo(request.getIdModelo());
        vehiculo.setPlaca(request.getPlaca().toUpperCase());
        vehiculo.setColor(request.getColor());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setDescripcion(request.getDescripcion());

        repository.save(vehiculo);
        return "Vehículo actualizado correctamente";
    }

    // prender o apagar el carro (estatus)
    public String cambiarEstatus(Integer idVehiculo, Integer idUsuario, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        if (idVehiculo == null) {
            throw new RuntimeException("El idVehiculo es obligatorio");
        }
        if (idUsuario == null) {
            throw new RuntimeException("El idUsuario es obligatorio");
        }

        VehiculosEntity vehiculo = repository.findById(idVehiculo).orElse(null);
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado");
        }

        // seguridad bola: ver si el carro es del mismo dueño de la sesion
        Integer idUsuarioToken = jwtUtils.getIdUsuarioFromJwtToken(token);
        Integer idRolToken = jwtUtils.getIdRolFromJwtToken(token);

        if (idRolToken != 1 && !idUsuario.equals(idUsuarioToken)) {
            throw new RuntimeException("Acceso denegado: no tienes permiso para cambiar el estatus de vehículos de otro usuario");
        }

        if (!vehiculo.getIdUsuario().equals(idUsuario)) {
            throw new RuntimeException("Acceso denegado: el vehículo no pertenece al usuario especificado");
        }

        // si lo quiere prender, ver que no tenga ya los 4 activos
        if (!vehiculo.getEstatus()) {
            long activos = repository.countByIdUsuarioAndEstatusTrue(idUsuario);
            if (activos >= 4) {
                throw new RuntimeException("El usuario ya tiene el límite de 4 vehículos activos");
            }
        }

        vehiculo.setEstatus(!vehiculo.getEstatus());
        repository.save(vehiculo);

        String nuevoEstatus;
        if (vehiculo.getEstatus()) {
            nuevoEstatus = "activo";
        } else {
            nuevoEstatus = "inactivo";
        }
        return "Estatus del vehículo actualizado a: " + nuevoEstatus;
    }

    public VehiculosEntity obtenerPorPlaca(String placa) {
        return repository.findByPlaca(placa).orElse(null);
    }
}