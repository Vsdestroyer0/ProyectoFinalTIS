package com.proyecto.TIS.users.usuarios.service;

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
        if (usuariosRepository.findByUsername("superadmin") == null) {
            UsuariosEntity admin = new UsuariosEntity();
            admin.setNombre("Super");
            admin.setApellidoPaterno("Administrador");
            admin.setApellidoMaterno("");
            admin.setEmail("superadmin@sicae.com");
            admin.setUsername("superadmin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setClaveUsuario("SA-001");
            admin.setEstatus(true); 
            admin.setTiempoCreacion(LocalDateTime.now());
            admin.setIdRol(1); 
            admin.setIdTipoUsuario(1); 
            admin.setIdProgramaEducativo(1); 

            usuariosRepository.save(admin);
            System.out.println(" SEEDER: Usuario administrador creado con éxito en la BD!");
            System.out.println(" Username: superadmin");
            System.out.println(" Password: admin123");
        }
    }
}
