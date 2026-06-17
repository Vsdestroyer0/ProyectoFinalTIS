package com.proyecto.TIS.Vehiculos.controller;

import com.proyecto.TIS.Vehiculos.dto.VehiculoRequestDTO;
import com.proyecto.TIS.Vehiculos.dto.VehiculoResponseDTO;
import com.proyecto.TIS.Vehiculos.service.VehiculosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculosController {

    @Autowired
    private VehiculosService service;

    // GET /api/vehiculos/{idUsuario} — Buscar vehículos de un usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> buscar(@PathVariable Integer idUsuario,
                                    @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            List<VehiculoResponseDTO> vehiculos = service.buscarPorUsuario(idUsuario, token);
            return ResponseEntity.ok(vehiculos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // POST /api/vehiculos — Registrar vehículo
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody VehiculoRequestDTO request,
                                       @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String respuesta = service.registrar(request, token);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // PUT /api/vehiculos/{idVehiculo} — Editar vehículo
    @PutMapping("/{idVehiculo}")
    public ResponseEntity<?> editar(@PathVariable Integer idVehiculo,
                                    @RequestBody VehiculoRequestDTO request,
                                    @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String respuesta = service.editar(idVehiculo, request, token);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // PATCH /api/vehiculos/{idVehiculo}/estatus — Cambiar estatus
    @PatchMapping("/{idVehiculo}/estatus")
    public ResponseEntity<?> cambiarEstatus(@PathVariable Integer idVehiculo,
                                            @RequestParam Integer idUsuario,
                                            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String respuesta = service.cambiarEstatus(idVehiculo, idUsuario, token);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // GET /api/vehiculos/placa/{placa} — Buscar vehículo por placa (para estacionamiento)
    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> obtenerPorPlaca(@PathVariable String placa) {
        com.proyecto.TIS.Vehiculos.entity.VehiculosEntity vehiculo = service.obtenerPorPlaca(placa);
        if (vehiculo == null) {
            return ResponseEntity.status(404).body("Vehículo no encontrado");
        }
        return ResponseEntity.ok(vehiculo);
    }
}