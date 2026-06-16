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
            SesionUsuarioDTO respuesta = authService.autenticar(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
