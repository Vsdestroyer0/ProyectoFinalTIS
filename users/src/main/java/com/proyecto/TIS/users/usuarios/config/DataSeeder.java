package com.proyecto.TIS.users.usuarios.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.proyecto.TIS.users.usuarios.entity.UsuariosEntity;
import com.proyecto.TIS.users.usuarios.repository.UsuariosRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        
        // Revisar si ya existe el admin para no duplicarlo cada vez que se prenda el servidor
        UsuariosEntity adminExistente = usuariosRepository.findByUsername("admin");
        
        if (adminExistente == null) {
            UsuariosEntity admin = new UsuariosEntity();
            admin.setNombre("Administrador");
            admin.setApellidoPaterno("Sistema");
            admin.setUsername("admin");
            admin.setEmail("admin@sicae.com");
            // La contraseña será "admin123" (la encriptamos antes de guardar)
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setTelefono("0000000000");
            admin.setClaveUsuario("ADM-001");
            admin.setEstatus(true);
            admin.setTiempoCreacion(LocalDateTime.now());
            
            admin.setIdRol(1); // 1 = Admin
            admin.setIdTipoUsuario(1); // Ajusta esto según tus IDs reales en BD
            admin.setIdProgramaEducativo(1); // Ajusta esto según tus IDs reales en BD

            // Usamos save() para que JPA (o MyBatis si estás en esa rama) lo guarde
            usuariosRepository.save(admin);
            
            System.out.println("======================================================");
            System.out.println("¡Seeder ejecutado! Usuario Administrador creado.");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
            System.out.println("======================================================");
        } else {
            System.out.println("El Administrador ya existe en la base de datos.");
        }
    }
}
