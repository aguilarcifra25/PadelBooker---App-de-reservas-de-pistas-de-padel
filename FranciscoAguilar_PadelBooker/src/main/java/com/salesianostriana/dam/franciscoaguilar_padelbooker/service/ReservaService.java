package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Cupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.ReservaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base.ServiciosBaseImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService extends ServiciosBaseImpl<Reserva, Long, ReservaRepository>{

	private final ReservaRepository reservaRepository;
	private final CuponService cuponService;
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

	public List<Reserva> buscarConFiltros(String usuario, String fecha, String horaEntrada) {
		
	    String u = null;
	    LocalDate f = null;
	    LocalTime h = null;
	    
	    
	    if (usuario != null && !usuario.isBlank()) {
	    	
	    	u = usuario.trim();
	    	
	    }	    		    	   	
	    
	    if (fecha != null && !fecha.isBlank()) {
	    	
	        f = LocalDate.parse(fecha.trim()); //"yyyy-MM-dd"
	        
	    }

	    
	    if (horaEntrada != null && !horaEntrada.isBlank()) {
	    	
	        h = LocalTime.parse(horaEntrada.trim()); //"HH:mm"
	        
	    }

	    return reservaRepository.buscarConFiltros(u, f, h);
	}
		
	
	public void crearReserva(Reserva reserva, Usuario usuario) {
		
		List<Reserva> reservasUsuario = reservaRepository.findByUsuario(usuario);
		
	    reservaRepository.save(reserva);	      
	    
	    cuponService.comprobarYGenerarCuponFidelizacion(usuario, reservasUsuario);
	}
	
	
	public List<Reserva> buscarPorUsuario(Usuario usuario) {
		
	    return reservaRepository.findByUsuario(usuario);
	    
	}
	
}	
