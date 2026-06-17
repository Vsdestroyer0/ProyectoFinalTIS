package com.proyecto.TIS.Estacionamiento.service;

import com.proyecto.TIS.Estacionamiento.entity.Espacio;
import com.proyecto.TIS.Estacionamiento.entity.Movimiento;
import org.springframework.stereotype.Service;
import com.proyecto.TIS.Estacionamiento.repository.EspacioRepository;
import com.proyecto.TIS.Estacionamiento.repository.MovimientoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service

public class EstacionamientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private EspacioRepository espacioRepository;

    public Movimiento registrarEntrada(Integer idVehiculo) throws Exception{
      if (movimientoRepository.contarMovimientosActivosPorVehiculo(idVehiculo)>0){
          throw new Exception("Este vehiculo ya se encuentra adentro del estacionamiento");
      }
      List<Espacio> espaciosDisponibles = espacioRepository.obtenerEspaciosDisponibles();
      if (espaciosDisponibles.isEmpty()){
          throw new Exception("El estacionamiento esta lleno");
      }
      Espacio espacioAsignado = espaciosDisponibles.get(0);
      LocalDateTime ahora = LocalDateTime.now();
      Movimiento nuevoMovimiento = new Movimiento();
      nuevoMovimiento.setIdVehiculo(idVehiculo);
      nuevoMovimiento.setIdSpace(espacioAsignado.getId());
      nuevoMovimiento.setTarHora(15.0);
      nuevoMovimiento.settEntrada(ahora);
      nuevoMovimiento.settCreacion(ahora);
      
      movimientoRepository.registrarEntrada(nuevoMovimiento);
      espacioRepository.actualizarEstadoEspacio(
               espacioAsignado.getId(),
               true
               );
               return nuevoMovimiento;
    }
}
