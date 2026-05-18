package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.ReservaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base.ServiciosBaseImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService extends ServiciosBaseImpl<Reserva, Long, ReservaRepository>{

	private final ReservaRepository reservaRepository;
	private final double precioLuzHora = 4.5;
	private final double precioRaqueta = 2;
	
	public double calcularHorasTotales (LocalTime horaEntrada, LocalTime horaSalida) {
		    
	    long minutosTotales;
	    double horasTotales;
	    
	    minutosTotales = Duration.between(horaEntrada, horaSalida).toMinutes();
	    horasTotales = minutosTotales / 60.0;  
	    
	    return horasTotales;
		
	}
	
	public double calcularPrecioTotal(LocalTime horaEntrada, LocalTime horaSalida, int cantidadRaquetas, double precioBasePista, boolean usaLuz) {
	    	    

	    double precioExtra = 0;
	    double horas = calcularHorasTotales(horaEntrada, horaSalida);	   
	    	
	    if (usaLuz) {
	    
	    	precioExtra = (precioLuzHora * horas);
	        	    
	    }
	    
	    precioExtra = precioExtra + (cantidadRaquetas * precioRaqueta);

	    return precioBasePista * horas + precioExtra;
	}
}
