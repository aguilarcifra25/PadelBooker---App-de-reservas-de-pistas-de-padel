package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionCupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Cupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.HistorialCupones;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.HistorialCuponesRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base.ServiciosBaseImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialCuponesService extends ServiciosBaseImpl<HistorialCupones, Long, HistorialCuponesRepository>{

    private final HistorialCuponesRepository historialCuponesRepo;
    private final CuponService cuponService;

    public void aplicarCuponAReserva(Reserva nuevaReserva, String codigoIntroducido, Usuario usuarioLogueado) {
        
        String codigo = codigoIntroducido.toUpperCase();
        Cupon cupon = cuponService.validarCupon(codigo, usuarioLogueado);
        HistorialCupones historial = new HistorialCupones(usuarioLogueado, codigo);
        
        double precioConDescuento;
        
        if (historialCuponesRepo.existsByUsuarioAndCodigoCupon(usuarioLogueado, codigo)) {
        	
            throw new ExcepcionCupon("Ya has canjeado este cupón en una reserva anterior."); 
            
        }
        
        nuevaReserva.setCupon(cupon); 

        precioConDescuento = cuponService.aplicarDescuento(nuevaReserva.getPrecioTotal(), cupon);
        
        nuevaReserva.setPrecioTotal(precioConDescuento);

        cuponService.gastarCupon(cupon);            
        
        historialCuponesRepo.save(historial);
    }
    
    public boolean cuponEnReserva (String codigoCupon) {
    	
    	return historialCuponesRepo.existsByCodigoCupon(codigoCupon);
    	
    }
    
}