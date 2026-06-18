package com.proyecto.TIS.users.usuarios.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.TIS.users.usuarios.dto.ActualizarUsuarioDTO;
import com.proyecto.TIS.users.usuarios.dto.RegistroUsuarioDTO;
import com.proyecto.TIS.users.usuarios.dto.PerfilUsuarioDTO;
import com.proyecto.TIS.users.usuarios.entity.UsuariosEntity;
import com.proyecto.TIS.users.usuarios.entity.RolEntity;
import com.proyecto.TIS.users.usuarios.entity.TipoUsuarioEntity;
import com.proyecto.TIS.users.usuarios.entity.ProgramaEducativoEntity;
import com.proyecto.TIS.users.usuarios.repository.UsuariosRepository;
import com.proyecto.TIS.users.usuarios.repository.RolRepository;
import com.proyecto.TIS.users.usuarios.repository.TipoUsuarioRepository;
import com.proyecto.TIS.users.usuarios.repository.ProgramaEducativoRepository;
import com.proyecto.TIS.users.security.JwtUtils;

@Service
public class UsuariosService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private ProgramaEducativoRepository programaEducativoRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(RegistroUsuarioDTO request, String token) {
        System.out.println("TELEFONO RECIBIDO EN DTO: " + request.getTelefono());
        
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Token JWT no proporcionado, inválido o expirado");
        }

        int idRol = jwtUtils.getIdRolFromJwtToken(token);
        if (idRol != 1) {
            throw new RuntimeException("El usuario autenticado que realiza la petición no tiene el rol de administrador");
        }

        // Validate mandatory fields
        if (request == null ||
            request.getNombre() == null || request.getNombre().trim().isEmpty() ||
            request.getApellidoPaterno() == null || request.getApellidoPaterno().trim().isEmpty() ||
            request.getCorreo() == null || request.getCorreo().trim().isEmpty() ||
            request.getUsername() == null || request.getUsername().trim().isEmpty() ||
            request.getPassword() == null || request.getPassword().trim().isEmpty() ||
            request.getTelefono() == null || request.getTelefono().trim().isEmpty() ||
            request.getIdRol() <= 0 || request.getIdTipoUsuario() <= 0 || request.getIdProgramaEducativo() <= 0) {
            throw new RuntimeException("Faltan datos obligatorios");
        }

        // Validate field sizes
        if (request.getNombre().length() > 50 ||
            request.getApellidoPaterno().length() > 50 ||
            (request.getApellidoMaterno() != null && request.getApellidoMaterno().length() > 50) ||
            request.getCorreo().length() > 255 ||
            request.getUsername().length() > 30 ||
            request.getTelefono().length() > 10) {
            throw new RuntimeException("Se excede el tamaño de los campos");
        }

        // parche de seguridad: que ningun gracioso se cree cuenta de admin solo pq si
        if (request.getIdRol() == 1) {
            throw new RuntimeException("Acceso denegado: No está permitido registrar nuevos usuarios con rol de Administrador");
        }

        if (!request.getCorreo().contains("@")) {
            throw new RuntimeException("El formato del correo es inválido");
        }

        if (usuariosRepository.existeUsuarioPorCorreoOUsername(request.getUsername(), request.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario registrado con el mismo correo electrónico o username");
        }
        
        String iniciales = request.getNombre().substring(0, 1) + request.getApellidoPaterno().substring(0, 1);
        if (request.getApellidoMaterno() != null && !request.getApellidoMaterno().trim().isEmpty()) {
            iniciales += request.getApellidoMaterno().substring(0, 1);
        }
        String claveGenerada = (iniciales + "-" + ((int)(Math.random() * 900) + 100)).toUpperCase();

        String passwdHash = passwordEncoder.encode(request.getPassword());

        UsuariosEntity newUser = new UsuariosEntity();
        newUser.setNombre(request.getNombre());
        newUser.setApellidoPaterno(request.getApellidoPaterno());
        newUser.setApellidoMaterno(request.getApellidoMaterno());
        newUser.setEmail(request.getCorreo());
        newUser.setUsername(request.getUsername());
        newUser.setTelefono(request.getTelefono());
        newUser.setPassword(passwdHash);
        newUser.setClaveUsuario(claveGenerada);
        newUser.setEstatus(true);
        newUser.setTiempoCreacion(LocalDateTime.now());
        newUser.setIdRol(request.getIdRol());
        newUser.setIdTipoUsuario(request.getIdTipoUsuario());
        newUser.setIdProgramaEducativo(request.getIdProgramaEducativo());

        usuariosRepository.save(newUser);
        return "Usuario registrado exitosamente con clave: " + claveGenerada;
    }

    public boolean exist(int idUsuario) {
        return usuariosRepository.existsById(idUsuario);
    }

    public boolean status(String claveUsuario) {
        UsuariosEntity user = usuariosRepository.findByClaveUsuario(claveUsuario);
        if (user == null) {
            return false;
        }
        return user.isEstatus();
    }

    public PerfilUsuarioDTO perf(int idUsuario, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Token JWT no proporcionado o inválido");
        }

        if (idUsuario <= 0) {
            throw new RuntimeException("No se proporcionó el identificador del usuario (idUsuario)");
        }

        UsuariosEntity user = usuariosRepository.findById(idUsuario).orElse(null);
        if (user == null) {
            throw new RuntimeException("El idUsuario solicitado no existe en la base de datos");
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

        String programaEducativoNombre = "Desconocido";
        ProgramaEducativoEntity programa = programaEducativoRepository.findById(user.getIdProgramaEducativo()).orElse(null);
        if (programa != null) {
            programaEducativoNombre = programa.getNombre();
        }

        String nombreCompleto = user.getNombre() + " " + user.getApellidoPaterno();
        if (user.getApellidoMaterno() != null && !user.getApellidoMaterno().trim().isEmpty()) {
            nombreCompleto = nombreCompleto + " " + user.getApellidoMaterno();
        }

        boolean active = user.isEstatus();

        return new PerfilUsuarioDTO(
            rolNombre,
            nombreCompleto,
            tipoUsuarioNombre,
            programaEducativoNombre,
            user.getUsername(),
            user.getEmail(),
            user.getTelefono(),
            active,
            user.getClaveUsuario(),
            user.getTiempoCreacion(),
            user.getTempoActualizacion()
        );
    }

    public String edit(int idUsuario, ActualizarUsuarioDTO request, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Token JWT no proporcionado o inválido");
        }

        // Detect editing protected fields
        if (request.getUsername() != null || request.getPassword() != null || request.getClaveUsuario() != null) {
            throw new RuntimeException("Intento de editar campos protegidos (usuario, contraseña, clave del usuario)");
        }

        // ver que no manden cosas vacias o nulas
        if (request.getNombre() == null || request.getNombre().trim().isEmpty() ||
            request.getApellidoPaterno() == null || request.getApellidoPaterno().trim().isEmpty() ||
            request.getCorreo() == null || request.getCorreo().trim().isEmpty() ||
            request.getTelefono() == null || request.getTelefono().trim().isEmpty() ||
            request.getIdRol() <= 0 || request.getIdTipoUsuario() <= 0 || request.getIdProgramaEducativo() <= 0) {
            throw new RuntimeException("Faltan datos obligatorios");
        }

        // validar que no se pasen de la longitud pq la base de datos se queja
        if (request.getNombre().length() > 50 ||
            request.getApellidoPaterno().length() > 50 ||
            (request.getApellidoMaterno() != null && request.getApellidoMaterno().length() > 50) ||
            request.getCorreo().length() > 255 ||
            request.getTelefono().length() != 10) {
            throw new RuntimeException("Tamaño de los campos excedido");
        }

        // ver si escribieron bien el email con arroba y eso
        if (!request.getCorreo().contains("@")) {
            throw new RuntimeException("El formato del correo es inválido");
        }

        // que no repitan el mismo correo en otra cuenta
        if (usuariosRepository.correoUsado(request.getCorreo(), idUsuario)) {
            throw new RuntimeException("El nuevo correo electrónico proporcionado ya pertenece a otro usuario");
        }

        UsuariosEntity user = usuariosRepository.findById(idUsuario).orElse(null);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // ver que solo pueda editar su propio perfil o que sea el mero admin
        String usernameToken = jwtUtils.getUserNameFromJwtToken(token);
        int idRolToken = jwtUtils.getIdRolFromJwtToken(token);
        
        if (idRolToken != 1 && !user.getUsername().equals(usernameToken)) {
            throw new RuntimeException("Acceso denegado: No tienes permiso para editar este perfil");
        }

        user.setIdRol(request.getIdRol());
        user.setIdTipoUsuario(request.getIdTipoUsuario());
        user.setIdProgramaEducativo(request.getIdProgramaEducativo());
        user.setNombre(request.getNombre());
        user.setApellidoPaterno(request.getApellidoPaterno());
        user.setApellidoMaterno(request.getApellidoMaterno());
        user.setEmail(request.getCorreo());
        user.setTelefono(request.getTelefono()); 
        user.setTempoActualizacion(LocalDateTime.now());

        usuariosRepository.save(user);
        return "Usuario actualizado correctamente";
    }

    public String changeStatus(int idUsuario, String token) {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new RuntimeException("Token JWT no proporcionado o inválido");
        }

        Integer idRol = jwtUtils.getIdRolFromJwtToken(token);
        if (idUsuario <= 0 || idRol == null || idRol <= 0) {
            throw new RuntimeException("Faltan datos obligatorios (idUsuario, idRol)");
        }

        if (idRol != 1) {
            throw new RuntimeException("El usuario que solicita la acción no tiene rol de administrador");
        }

        UsuariosEntity user = usuariosRepository.findById(idUsuario).orElse(null);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (user.isEstatus()) {
            user.setEstatus(false);
        } else {
            user.setEstatus(true);
        }
        usuariosRepository.save(user);

        return "Estatus del usuario actualizado correctamente";
    }

    public UsuariosEntity obtenerPorClave(String claveUsuario) {
        return usuariosRepository.findByClaveUsuario(claveUsuario);
    }
}
