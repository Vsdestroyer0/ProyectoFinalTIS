package com.proyecto.TIS.users.usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.TIS.users.usuarios.dto.ActualizarUsuarioDTO;
import com.proyecto.TIS.users.usuarios.dto.RegistroUsuarioDTO;
import com.proyecto.TIS.users.usuarios.dto.PerfilUsuarioDTO;
import com.proyecto.TIS.users.usuarios.service.UsuariosService;

@RestController
@RequestMapping("/api/user")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    private ResponseEntity<?> handleException(RuntimeException e) {
        String msg = e.getMessage();
        if (msg == null) {
            return ResponseEntity.status(400).body("Error de validación");
        }
        if (msg.contains("Token JWT") || msg.contains("Credenciales incorrectas")) {
            return ResponseEntity.status(401).body(msg);
        }
        if (msg.contains("rol de administrador") || msg.contains("No tienes permiso") || msg.contains("Acceso denegado")) {
            return ResponseEntity.status(403).body(msg);
        }
        if (msg.contains("no existe") || msg.contains("Usuario no encontrado")) {
            return ResponseEntity.status(404).body(msg);
        }
        if (msg.contains("ya está registrado") || msg.contains("ya pertenece")) {
            return ResponseEntity.status(409).body(msg);
        }
        return ResponseEntity.status(400).body(msg);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistroUsuarioDTO userRequest,
                                          @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Token JWT no proporcionado, inválido o expirado");
            }
            String token = authHeader.substring(7);
            String response = usuariosService.register(userRequest, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return handleException(e);
        }
    }

    @GetMapping("/{idUsuario}/exist")
    public ResponseEntity<?> existUser(@PathVariable int idUsuario){
        boolean exist = usuariosService.exist(idUsuario);
        return ResponseEntity.ok(exist);
    }

    @GetMapping("/{claveUsuario}/status")
    public ResponseEntity<?> statusUser(@PathVariable String claveUsuario){
        boolean active = usuariosService.status(claveUsuario);
        return ResponseEntity.ok(active);
    }

    @GetMapping("/profile/{idUsuario}")
    public ResponseEntity<?> perfUser(@PathVariable int idUsuario,
                                      @RequestHeader(value = "Authorization", required = false) String authHeader){
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Token JWT no proporcionado o inválido");
            }
            String token = authHeader.substring(7);
            PerfilUsuarioDTO user = usuariosService.perf(idUsuario, token);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return handleException(e);
        }
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> editUser(@PathVariable int idUsuario,
                                      @RequestBody ActualizarUsuarioDTO editRequest,
                                      @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Token JWT no proporcionado o inválido");
            }
            String token = authHeader.substring(7);
            String response = usuariosService.edit(idUsuario, editRequest, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return handleException(e);
        }
    }

    @PatchMapping("/{idUsuario}/estatus")
    public ResponseEntity<?> changeStatusUser(@PathVariable int idUsuario,
                                              @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Token JWT no proporcionado o inválido");
            }
            String token = authHeader.substring(7);
            String response = usuariosService.changeStatus(idUsuario, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return handleException(e);
        }
    }

    @GetMapping("/clave/{claveUsuario}")
    public ResponseEntity<?> getUserByClave(@PathVariable String claveUsuario) {
        com.proyecto.TIS.users.usuarios.entity.UsuariosEntity user = usuariosService.obtenerPorClave(claveUsuario);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        return ResponseEntity.ok(user);
    }
}
