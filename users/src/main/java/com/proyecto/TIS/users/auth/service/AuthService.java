package com.proyecto.TIS.users.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.TIS.users.auth.dto.SesionUsuarioDTO;
import com.proyecto.TIS.users.usuarios.entity.UsuariosEntity;
import com.proyecto.TIS.users.usuarios.entity.RolEntity;
import com.proyecto.TIS.users.usuarios.entity.TipoUsuarioEntity;
import com.proyecto.TIS.users.usuarios.repository.UsuariosRepository;
import com.proyecto.TIS.users.usuarios.repository.RolRepository;
import com.proyecto.TIS.users.usuarios.repository.TipoUsuarioRepository;
import com.proyecto.TIS.users.security.JwtUtils;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SesionUsuarioDTO autenticar(String username, String password) {
        UsuariosEntity user = usuariosRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        if (!user.isEstatus()) {
            throw new RuntimeException("El usuario está registrado pero su estatus es inactivo");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtUtils.generateTokenFromUsername(user.getUsername(), user.getIdRol(), user.getIdUsuario());
        
        // pegar el nombre y apellidos para que no se vea feo
        String nombreCompleto = user.getNombre() + " " + user.getApellidoPaterno();
        if (user.getApellidoMaterno() != null && !user.getApellidoMaterno().trim().isEmpty()) {
            nombreCompleto = nombreCompleto + " " + user.getApellidoMaterno();
        }

        String rolNombre = "Desconocido";
        RolEntity rol = rolRepository.findById(user.getIdRol()).orElse(null);
        if (rol != null) {
            rolNombre = rol.getNombre();
        }

        String tipoUsuarioNombre = "Desconocido";
        TipoUsuarioEntity tipo = tipoUsuarioRepository.findById(user.getIdTipoUsuario()).orElse(null);
        if (tipo != null) {
            tipoUsuarioNombre = tipo.getNombre();
        }

        return new SesionUsuarioDTO(
            user.getIdUsuario(),
            user.getIdRol(),
            rolNombre,
            user.getUsername(),
            nombreCompleto,
            user.getIdTipoUsuario(),
            tipoUsuarioNombre,
            token
        );
    }
}
