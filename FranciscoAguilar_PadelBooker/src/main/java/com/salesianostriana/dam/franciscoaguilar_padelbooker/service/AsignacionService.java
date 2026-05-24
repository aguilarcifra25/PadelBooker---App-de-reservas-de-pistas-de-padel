package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Asignacion;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.AsignacionPK;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.AsignacionRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base.ServiciosBaseImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionService extends ServiciosBaseImpl<Asignacion, AsignacionPK, AsignacionRepository> {

	private final AsignacionRepository asignacionRepository;
	
	public Asignacion registrarAsignacionCompleta(Reserva reserva, Pista pista, boolean usaLuz, int cantRaquetas, double precioTotal) {
                
        AsignacionPK pk = new AsignacionPK(pista.getNumero(), reserva.getCodigo());
        
        Asignacion asignacion = Asignacion.builder()
                .asignacionPK(pk)
                .reserva(reserva)
                .pista(pista)
                .usaLuz(usaLuz)
                .cantRaquetas(cantRaquetas)
                .precio(precioTotal)
                .observaciones("Reserva de Dam Padel en la web.")
                .build();
        
        
        return asignacionRepository.save(asignacion);
    }
	
}
