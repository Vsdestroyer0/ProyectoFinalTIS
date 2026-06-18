package com.proyecto.TIS.users.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.TIS.users.auth.dto.CredencialesDTO;
import com.proyecto.TIS.users.auth.dto.SesionUsuarioDTO;
import com.proyecto.TIS.users.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody CredencialesDTO loginRequest) {
        try {
            if (loginRequest == null ||
                loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return ResponseEntity.status(400).body("Faltan datos obligatorios (usuario y contraseña)");
            }
            if (loginRequest.getUsername().length() > 30 || loginRequest.getPassword().length() > 255) {
                return ResponseEntity.status(400).body("Se excede el tamaño máximo permitido de los campos");
            }
            SesionUsuarioDTO respuesta = authService.autenticar(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if ("Credenciales incorrectas".equals(msg)) {
                return ResponseEntity.status(401).body(msg);
            }
            if ("El usuario está registrado pero su estatus es inactivo".equals(msg)) {
                return ResponseEntity.status(403).body(msg);
            }
            return ResponseEntity.status(400).body(msg);
        }
    }
}
