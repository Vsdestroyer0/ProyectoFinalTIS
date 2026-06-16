package com.proyecto.TIS.Vehiculos.controller;

import com.proyecto.TIS.Vehiculos.entity.VehiculosEntity;
import com.proyecto.TIS.Vehiculos.service.VehiculosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculos")
public class VehiculosController {

    @Autowired
    private VehiculosService service;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody VehiculosEntity vehiculo) {
        try {
            VehiculosEntity nuevoVehiculo = service.guardarVehiculo(vehiculo);
            return ResponseEntity.ok(nuevoVehiculo);
        } catch (Exception e) {
            // mensaje de error questa en el service
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}