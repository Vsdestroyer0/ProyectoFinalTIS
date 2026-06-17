package com.proyecto.TIS.Estacionamiento.controller;

import com.proyecto.TIS.Estacionamiento.dto.EntradaDTO;
import com.proyecto.TIS.Estacionamiento.dto.SalidaDTO;
import com.proyecto.TIS.Estacionamiento.entity.Espacio;
import com.proyecto.TIS.Estacionamiento.entity.Movimiento;
import com.proyecto.TIS.Estacionamiento.service.EstacionamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estacionamiento")
public class EstacionamientoController {

    @Autowired
    private EstacionamientoService service;

    // endpoint para meter el carro
    @PostMapping("/entrada")
    public ResponseEntity<?> registrarEntrada(@RequestBody EntradaDTO dto,
                                              @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Acceso denegado: Token de autenticación ausente o inválido");
            }
            String token = authHeader.substring(7);
            Movimiento movimiento = service.registrarEntrada(dto, token);

            Map<String, Object> response = new HashMap<>();
            response.put("idMovimiento", movimiento.getIdMove());
            response.put("tiempoEntrada", movimiento.gettEntrada());
            response.put("idEspacio", movimiento.getIdSpace());
            response.put("tarifaHora", movimiento.getTarHora());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // endpoint para sacar el carro y cobrarle
    @PostMapping("/salida")
    public ResponseEntity<?> registrarSalida(@RequestBody SalidaDTO dto,
                                             @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Acceso denegado: Token de autenticación ausente o inválido");
            }
            String token = authHeader.substring(7);
            Movimiento movimiento = service.registrarSalida(dto, token);

            Map<String, Object> response = new HashMap<>();
            response.put("idMovimiento", movimiento.getIdMove());
            response.put("tiempoEntrada", movimiento.gettEntrada());
            response.put("tiempoSalida", movimiento.gettSalida());
            response.put("idEspacio", movimiento.getIdSpace());
            response.put("tarifaHora", movimiento.getTarHora());
            response.put("costoTotal", movimiento.getCostoT());
            response.put("horasCobradas", movimiento.gethCobradas());
            response.put("minutosEstacionado", movimiento.getMinEstacionado());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // endpoint para ver que cajones estan libres
    @GetMapping("/espacios")
    public ResponseEntity<?> consultarEspacios(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Acceso denegado: Token de autenticación ausente o inválido");
            }
            String token = authHeader.substring(7);
            List<Espacio> espacios = service.consultarEspacios(token);
            return ResponseEntity.ok(espacios);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
