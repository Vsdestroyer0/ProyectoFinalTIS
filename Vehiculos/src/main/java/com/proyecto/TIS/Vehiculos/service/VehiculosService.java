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

    // Generar clave única de vehículo estilo V-XXX
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

    // GET: vehículos de un usuario
    public List<VehiculoResponseDTO> buscarPorUsuario(Integer idUsuario, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        List<VehiculosEntity> vehiculos = repository.findByIdUsuario(idUsuario);
        List<VehiculoResponseDTO> resultado = new ArrayList<>();
        for (VehiculosEntity v : vehiculos) {
            resultado.add(buildResponse(v));
        }
        return resultado;
    }

    // POST: registrar vehículo
    public String registrar(VehiculoRequestDTO request, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        // Validar campos obligatorios
        if (request.getIdUsuario() == null || request.getIdModelo() == null
                || request.getPlaca() == null || request.getPlaca().isBlank()
                || request.getColor() == null || request.getColor().isBlank()
                || request.getAnio() == null) {
            throw new RuntimeException("Faltan datos obligatorios: idUsuario, idModelo, placa, color y año son requeridos");
        }

        // Máximo 4 vehículos activos
        long activos = repository.countByIdUsuarioAndEstatusTrue(request.getIdUsuario());
        if (activos >= 4) {
            throw new RuntimeException("El usuario ya tiene el límite de 4 vehículos activos");
        }

        // Placa no duplicada
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
        nuevo.setEstatus(true);
        nuevo.setClaveVehiculo(generarClaveVehiculo());

        repository.save(nuevo);
        return "Vehículo registrado correctamente con clave: " + nuevo.getClaveVehiculo();
    }

    // PUT: editar vehículo
    public String editar(Integer idVehiculo, VehiculoRequestDTO request, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        VehiculosEntity vehiculo = repository.findById(idVehiculo).orElse(null);
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado");
        }

        // Solo puede editar su propio vehículo
        if (!vehiculo.getIdUsuario().equals(request.getIdUsuario())) {
            throw new RuntimeException("Acceso denegado: solo puedes editar tus propios vehículos");
        }

        // Placa no duplicada (excluyendo el propio vehículo)
        if (repository.existsByPlacaAndIdVehiculoNot(request.getPlaca(), idVehiculo)) {
            throw new RuntimeException("Ya existe otro vehículo con la placa: " + request.getPlaca());
        }

        vehiculo.setIdModelo(request.getIdModelo());
        vehiculo.setPlaca(request.getPlaca().toUpperCase());
        vehiculo.setColor(request.getColor());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setDescripcion(request.getDescripcion());

        repository.save(vehiculo);
        return "Vehículo actualizado correctamente";
    }

    // PATCH: cambiar estatus
    public String cambiarEstatus(Integer idVehiculo, Integer idUsuario, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        VehiculosEntity vehiculo = repository.findById(idVehiculo).orElse(null);
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado");
        }

        // Solo puede cambiar estatus de su propio vehículo
        if (!vehiculo.getIdUsuario().equals(idUsuario)) {
            throw new RuntimeException("Acceso denegado: solo puedes cambiar el estatus de tus propios vehículos");
        }

        vehiculo.setEstatus(!vehiculo.getEstatus());
        repository.save(vehiculo);

        String nuevoEstatus = vehiculo.getEstatus() ? "activo" : "inactivo";
        return "Estatus del vehículo actualizado a: " + nuevoEstatus;
    }
}