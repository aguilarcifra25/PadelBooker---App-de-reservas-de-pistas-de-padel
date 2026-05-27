package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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
	
	private boolean seSolapan(LocalTime inicioA, LocalTime finA, LocalTime inicioB, LocalTime finB) {
        return inicioA.isBefore(finB) && finA.isAfter(inicioB);
    }

	public boolean tieneConflictoHorario(long numeroPista, Date fechaNueva, LocalTime inicioNueva, LocalTime finNueva) {
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String fechaNuevaStr = sdf.format(fechaNueva);

	    List<Reserva> todasLasReservas = this.buscarTodos();

	    return todasLasReservas.stream()
	            .filter(r -> r.getFecha() != null && sdf.format(r.getFecha()).equals(fechaNuevaStr))
	            .flatMap(r -> r.getAsignaciones().stream())
	            .filter(a -> a.getPista() != null && a.getPista().getNumero().equals(numeroPista))
	            .anyMatch(a -> seSolapan(inicioNueva, finNueva, a.getReserva().getHoraEntrada(), a.getReserva().getHoraSalida()));
	}
}
