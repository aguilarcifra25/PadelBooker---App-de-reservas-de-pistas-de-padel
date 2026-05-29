package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	private final double precioPala = 2;
	
	public double calcularHorasTotales (LocalTime horaEntrada, LocalTime horaSalida) {
		    
	    long minutosTotales;
	    double horasTotales;
	    
	    minutosTotales = Duration.between(horaEntrada, horaSalida).toMinutes();
	    horasTotales = minutosTotales / 60.0;  
	    
	    return horasTotales;
		
	}
	
	public double calcularPrecioLuz (LocalTime horaEntrada, LocalTime horaSalida, boolean usaLuz ) {
		
		double horas = calcularHorasTotales(horaEntrada, horaSalida);
		
		if (usaLuz) {
			
			return precioLuzHora * horas;
			
		} else {
			
			return 0;
			
		}
				
		
	}
	
	public double calcularPrecioPalas(int cantidadRaquetas) {
		
		return cantidadRaquetas * precioPala;
		
	}
	
	public double calcularPrecioTotal(LocalTime horaEntrada, LocalTime horaSalida, int cantidadRaquetas, double precioBasePista, boolean usaLuz) {
	    	    

	    double precioExtra;
	    double horas = calcularHorasTotales(horaEntrada, horaSalida);	   
	    
	    precioExtra = calcularPrecioLuz(horaEntrada, horaSalida, usaLuz) + calcularPrecioPalas(cantidadRaquetas);

	    return precioBasePista * horas + precioExtra;
	}
	
	private boolean seSolapan(LocalTime inicioA, LocalTime finA, LocalTime inicioB, LocalTime finB) {
        return inicioA.isBefore(finB) && finA.isAfter(inicioB);
    }

	public boolean tieneConflictoHorario(long numeroPista, LocalDate fechaNueva, LocalTime inicioNueva, LocalTime finNueva) {
	    
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String fechaStr = fechaNueva.format(formato);
	    
	    LocalDate fecha = LocalDate.parse(fechaStr);
	    
	    List<Reserva> todasLasReservas = this.buscarTodos();

	    return todasLasReservas.stream()
	            .filter(r -> r.getFecha() != null && r.getFecha().equals(fecha))
	            .flatMap(r -> r.getAsignaciones().stream())
	            					.filter(a -> a.getPista() != null && a.getPista().getNumero().equals(numeroPista))
	            					.anyMatch(a -> seSolapan(inicioNueva, finNueva, a.getReserva().getHoraEntrada(), a.getReserva().getHoraSalida()));
	    	    
	}
	
	//Cambia en el return mirando que no sea la misma reserva
	public boolean tieneConflictoHorarioEdicion(long numeroPista, LocalDate fechaNueva, LocalTime inicioNueva, LocalTime finNueva, Long codigoReservaActual) {
	    
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String fechaStr = fechaNueva.format(formato);
	    LocalDate fecha = LocalDate.parse(fechaStr);
	    
	    List<Reserva> todasLasReservas = this.buscarTodos();

	    return todasLasReservas.stream()
	            
	            .filter(r -> r.getCodigo() != null && !r.getCodigo().equals(codigoReservaActual))	            
	            .filter(r -> r.getFecha() != null && r.getFecha().equals(fecha))
	            .flatMap(r -> r.getAsignaciones().stream())	            
	            				.filter(a -> a.getPista() != null && a.getPista().getNumero().equals(numeroPista))	           
	            				.anyMatch(a -> seSolapan(inicioNueva, finNueva, a.getReserva().getHoraEntrada(), a.getReserva().getHoraSalida()));
	}		
}
