package com.proyecto.TIS.Vehiculos.service;

import com.proyecto.TIS.Vehiculos.entity.VehiculosEntity;
import com.proyecto.TIS.Vehiculos.repository.VehiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculosService {

    @Autowired
    private VehiculosRepository repository;

    // guardar un vehiculo
    public VehiculosEntity guardarVehiculo(VehiculosEntity vehiculo) throws Exception {
        
        // 1 maximo 4 vehiculos acrivos
        long cuentaActivos = repository.countByIdUsuarioAndEstatusTrue(vehiculo.getIdUsuario());
        if (cuentaActivos >= 4) {
            throw new Exception("El usuario ya tiene el límite de 4 vehículos activos.");
        }

        // 2 no placas duplicadas
        if (repository.existsByPlaca(vehiculo.getPlaca())) {
            throw new Exception("Ya existe un vehículo registrado con esta placa: " + vehiculo.getPlaca());
        }

        // guardar si todo sale bien
        return repository.save(vehiculo);
    }
    
    // metodo para obtener vehiculo por id
    public VehiculosEntity obtenerVehiculo(Integer id) {
        return repository.findById(id).orElse(null);
    }
}