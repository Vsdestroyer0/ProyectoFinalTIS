package com.proyecto.TIS.Estacionamiento.service;

import com.proyecto.TIS.Estacionamiento.entity.Espacio;
import com.proyecto.TIS.Estacionamiento.entity.Movimiento;
import com.proyecto.TIS.Estacionamiento.dto.EntradaDTO;
import com.proyecto.TIS.Estacionamiento.dto.SalidaDTO;
import com.proyecto.TIS.Estacionamiento.security.JwtUtils;
import org.springframework.stereotype.Service;
import com.proyecto.TIS.Estacionamiento.repository.EspacioRepository;
import com.proyecto.TIS.Estacionamiento.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EstacionamientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${usuarios.service.url}")
    private String usuariosServiceUrl;

    @Value("${vehiculos.service.url}")
    private String vehiculosServiceUrl;

    // Registrar movimiento (entrada)
    public Movimiento registrarEntrada(EntradaDTO dto, String token) throws Exception {
        // 1. Validar datos obligatorios
        if (dto.getClaveU() == null || dto.getClaveU().trim().isEmpty()) {
            throw new Exception("La clave del usuario es obligatoria");
        }
        if (dto.getPlaca() == null || dto.getPlaca().trim().isEmpty()) {
            throw new Exception("La placa del vehículo es obligatoria");
        }
        if (dto.getTarHora() == null) {
            throw new Exception("La tarifa por hora es obligatoria");
        }
        if (dto.getIdSpace() == null) {
            throw new Exception("El espacio es obligatorio");
        }
        if (dto.gettEntrada() == null) {
            throw new Exception("El tiempo de entrada es obligatorio");
        }
        if (dto.gettCreacion() == null) {
            throw new Exception("El tiempo de creación es obligatorio");
        }

        // 2. Validar que el usuario esté activo y registrado (llamada RestTemplate)
        String userUrl = usuariosServiceUrl + "/api/user/clave/" + dto.getClaveU();
        Map<String, Object> userResponse;
        try {
            userResponse = restTemplate.getForObject(userUrl, Map.class);
        } catch (Exception e) {
            throw new Exception("El usuario no existe o no se pudo validar");
        }
        if (userResponse == null) {
            throw new Exception("El usuario no existe");
        }
        Boolean estatusUser = (Boolean) userResponse.get("estatus");
        if (estatusUser == null || !estatusUser) {
            throw new Exception("El usuario está inactivo");
        }
        Integer idUsuario = (Integer) userResponse.get("idUsuario");

        // 3. Validar que el vehículo esté registrado, activo y pertenezca al usuario (llamada RestTemplate)
        String vehicleUrl = vehiculosServiceUrl + "/api/vehiculos/placa/" + dto.getPlaca();
        Map<String, Object> vehicleResponse;
        try {
            vehicleResponse = restTemplate.getForObject(vehicleUrl, Map.class);
        } catch (Exception e) {
            throw new Exception("El vehículo no está registrado o no se pudo validar");
        }
        if (vehicleResponse == null) {
            throw new Exception("El vehículo no está registrado");
        }
        Boolean estatusVehicle = (Boolean) vehicleResponse.get("estatus");
        if (estatusVehicle == null || !estatusVehicle) {
            throw new Exception("El vehículo está inactivo");
        }
        Integer idUsuarioVehiculo = (Integer) vehicleResponse.get("idUsuario");
        if (idUsuarioVehiculo == null || !idUsuarioVehiculo.equals(idUsuario)) {
            throw new Exception("El vehículo no pertenece al usuario especificado");
        }
        Integer idVehiculo = (Integer) vehicleResponse.get("idVehiculo");

        // 4. Validar límite de máximo 2 vehículos activos adentro por usuario
        // Obtenemos la lista de todos los vehículos del usuario y verificamos cuántos están en el estacionamiento
        String userVehiclesUrl = vehiculosServiceUrl + "/api/vehiculos/" + idUsuario;
        List<Map<String, Object>> vehiclesList;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<List> response = restTemplate.exchange(
                userVehiclesUrl,
                HttpMethod.GET,
                entity,
                List.class
            );
            vehiclesList = response.getBody();
        } catch (Exception e) {
            throw new Exception("Error al consultar vehículos del usuario: " + e.getMessage());
        }

        int activeInside = 0;
        if (vehiclesList != null) {
            for (Map<String, Object> v : vehiclesList) {
                Integer vid = (Integer) v.get("idVehiculo");
                if (vid != null) {
                    if (movimientoRepository.contarMovimientosActivosPorVehiculo(vid) > 0) {
                        activeInside++;
                    }
                }
            }
        }
        if (activeInside >= 2) {
            throw new Exception("El usuario ya tiene el límite máximo de 2 vehículos en el estacionamiento");
        }

        // 5. Validar que el carro no esté ya adentro
        if (movimientoRepository.contarMovimientosActivosPorVehiculo(idVehiculo) > 0) {
            throw new Exception("El vehículo ya se encuentra adentro del estacionamiento");
        }

        // 6. Validar y asignar espacio
        Espacio espacio = espacioRepository.obtenerPorId(dto.getIdSpace());
        if (espacio == null) {
            throw new Exception("El espacio especificado no existe");
        }
        if (espacio.getOcupado()) {
            throw new Exception("El espacio especificado ya está ocupado");
        }

        // 7. Crear el movimiento
        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setIdVehiculo(idVehiculo);
        nuevoMovimiento.setIdSpace(dto.getIdSpace());
        nuevoMovimiento.setTarHora(dto.getTarHora());
        nuevoMovimiento.settEntrada(dto.gettEntrada());
        nuevoMovimiento.settCreacion(dto.gettCreacion());

        movimientoRepository.registrarEntrada(nuevoMovimiento);

        // 8. Ocupar el espacio
        espacioRepository.actualizarEstadoEspacio(dto.getIdSpace(), true);

        return nuevoMovimiento;
    }

    // Registrar movimiento (salida)
    public Movimiento registrarSalida(SalidaDTO dto, String token) throws Exception {
        // 1. Validar datos obligatorios
        if (dto.getClaveU() == null || dto.getClaveU().trim().isEmpty()) {
            throw new Exception("La clave del usuario es obligatoria");
        }
        if (dto.getPlaca() == null || dto.getPlaca().trim().isEmpty()) {
            throw new Exception("La placa del vehículo es obligatoria");
        }
        if (dto.gettSalida() == null) {
            throw new Exception("El tiempo de salida es obligatorio");
        }
        if (dto.gettActualizacion() == null) {
            throw new Exception("El tiempo de actualización es obligatorio");
        }
        if (dto.getCostoT() == null) {
            throw new Exception("El costo total es obligatorio");
        }
        if (dto.gethCobradas() == null) {
            throw new Exception("Las horas cobradas son obligatorias");
        }
        if (dto.getMinEstacionado() == null) {
            throw new Exception("Los minutos estacionado son obligatorios");
        }

        // 2. Validar que el usuario esté activo y registrado (llamada RestTemplate)
        String userUrl = usuariosServiceUrl + "/api/user/clave/" + dto.getClaveU();
        Map<String, Object> userResponse;
        try {
            userResponse = restTemplate.getForObject(userUrl, Map.class);
        } catch (Exception e) {
            throw new Exception("El usuario no existe o no se pudo validar");
        }
        if (userResponse == null) {
            throw new Exception("El usuario no existe");
        }
        Boolean estatusUser = (Boolean) userResponse.get("estatus");
        if (estatusUser == null || !estatusUser) {
            throw new Exception("El usuario está inactivo");
        }
        Integer idUsuario = (Integer) userResponse.get("idUsuario");

        // 3. Validar que el vehículo esté registrado y pertenezca al usuario (llamada RestTemplate)
        String vehicleUrl = vehiculosServiceUrl + "/api/vehiculos/placa/" + dto.getPlaca();
        Map<String, Object> vehicleResponse;
        try {
            vehicleResponse = restTemplate.getForObject(vehicleUrl, Map.class);
        } catch (Exception e) {
            throw new Exception("El vehículo no está registrado o no se pudo validar");
        }
        if (vehicleResponse == null) {
            throw new Exception("El vehículo no está registrado");
        }
        Integer idUsuarioVehiculo = (Integer) vehicleResponse.get("idUsuario");
        if (idUsuarioVehiculo == null || !idUsuarioVehiculo.equals(idUsuario)) {
            throw new Exception("El vehículo no pertenece al usuario especificado");
        }
        Integer idVehiculo = (Integer) vehicleResponse.get("idVehiculo");

        // 4. Buscar el movimiento activo del vehículo
        Movimiento movimiento = movimientoRepository.obtenerMovimientoActivoPorVehiculo(idVehiculo);
        if (movimiento == null) {
            throw new Exception("El vehículo no se encuentra adentro del estacionamiento");
        }

        // 5. Registrar salida
        movimiento.settSalida(dto.gettSalida());
        movimiento.settActualizacion(dto.gettActualizacion());
        movimiento.setMinEstacionado(dto.getMinEstacionado());
        movimiento.sethCobradas(dto.gethCobradas());
        movimiento.setCostoT(dto.getCostoT());

        movimientoRepository.registrarSalida(movimiento);

        // 6. Liberar el espacio
        espacioRepository.actualizarEstadoEspacio(movimiento.getIdSpace(), false);

        return movimiento;
    }

    // Consultar espacios disponibles
    public List<Espacio> consultarEspacios(String token) throws Exception {
        if (!jwtUtils.validateJwtToken(token)) {
            throw new Exception("Acceso denegado: Token inválido o expirado");
        }
        return espacioRepository.obtenerEspaciosDisponibles();
    }
}
