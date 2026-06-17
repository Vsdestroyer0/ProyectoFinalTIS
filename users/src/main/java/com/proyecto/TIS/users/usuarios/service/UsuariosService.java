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
        int idRol = jwtUtils.getIdRolFromJwtToken(token);
        if (idRol != 1) {
            throw new RuntimeException("Acceso denegado: Solo los administradores pueden registrar usuarios");
        }

        // Fix de seguridad: Impedir que se creen cuentas con Rol de Administrador
        if (request.getIdRol() == 1) {
            throw new RuntimeException("Acceso denegado: No está permitido registrar nuevos usuarios con rol de Administrador");
        }

        if (!request.getCorreo().contains("@")) {
            throw new RuntimeException("El formato del correo es inválido");
        }

        if (usuariosRepository.existeUsuarioPorCorreoOUsername(request.getUsername(), request.getCorreo())) {
            throw new RuntimeException("Usuario con mismo correo o username ya registrado");
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
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        UsuariosEntity user = usuariosRepository.findById(idUsuario).orElse(null);
        if (user == null) {
            throw new RuntimeException("Usuario con id " + idUsuario + " no existe");
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
            throw new RuntimeException("Acceso denegado: Token inválido o expirado");
        }

        // 1. Validar datos obligatorios
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }
        if (request.getApellidoPaterno() == null || request.getApellidoPaterno().trim().isEmpty()) {
            throw new RuntimeException("El apellido paterno es obligatorio");
        }
        if (request.getCorreo() == null || request.getCorreo().trim().isEmpty()) {
            throw new RuntimeException("El correo es obligatorio");
        }
        if (request.getTelefono() == null || request.getTelefono().trim().isEmpty()) {
            throw new RuntimeException("El teléfono es obligatorio");
        }
        if (request.getIdRol() <= 0) {
            throw new RuntimeException("El rol es obligatorio y debe ser válido");
        }
        if (request.getIdTipoUsuario() <= 0) {
            throw new RuntimeException("El tipo de usuario es obligatorio y debe ser válido");
        }
        if (request.getIdProgramaEducativo() <= 0) {
            throw new RuntimeException("El programa educativo es obligatorio y debe ser válido");
        }

        // 2. Validar tamaño de los campos
        if (request.getNombre().length() > 50) {
            throw new RuntimeException("El nombre no puede exceder los 50 caracteres");
        }
        if (request.getApellidoPaterno().length() > 50) {
            throw new RuntimeException("El apellido paterno no puede exceder los 50 caracteres");
        }
        if (request.getApellidoMaterno() != null && request.getApellidoMaterno().length() > 50) {
            throw new RuntimeException("El apellido materno no puede exceder los 50 caracteres");
        }
        if (request.getCorreo().length() > 255) {
            throw new RuntimeException("El correo no puede exceder los 255 caracteres");
        }
        if (request.getTelefono().length() != 10) {
            throw new RuntimeException("El teléfono debe tener exactamente 10 caracteres");
        }

        // 3. Validar formato de correo
        if (!request.getCorreo().contains("@")) {
            throw new RuntimeException("El formato del correo es inválido");
        }

        // 4. Validar mediante el correo electrónico que no existan usuarios repetidos
        if (usuariosRepository.correoUsado(request.getCorreo(), idUsuario)) {
            throw new RuntimeException("El correo ya está registrado en otra cuenta");
        }

        UsuariosEntity user = usuariosRepository.findById(idUsuario).orElse(null);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // 5. Validar que el usuario que intenta editar sea el dueño del perfil o un administrador
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
        int idRol = jwtUtils.getIdRolFromJwtToken(token);
        if (idRol != 1) {
            throw new RuntimeException("Acceso denegado: Solo los administradores pueden cambiar el estatus de un usuario");
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
