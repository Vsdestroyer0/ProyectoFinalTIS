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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistroUsuarioDTO userRequest, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String response = usuariosService.register(userRequest, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
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
    public ResponseEntity<?> perfUser(@PathVariable int idUsuario, @RequestHeader("Authorization") String authHeader){
        try {
            String token = authHeader.substring(7);
            PerfilUsuarioDTO user = usuariosService.perf(idUsuario, token);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> editUser(@PathVariable int idUsuario, @RequestBody ActualizarUsuarioDTO editRequest, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String response = usuariosService.edit(idUsuario, editRequest, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PatchMapping("/{idUsuario}/estatus")
    public ResponseEntity<?> changeStatusUser(@PathVariable int idUsuario, @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String response = usuariosService.changeStatus(idUsuario, token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
