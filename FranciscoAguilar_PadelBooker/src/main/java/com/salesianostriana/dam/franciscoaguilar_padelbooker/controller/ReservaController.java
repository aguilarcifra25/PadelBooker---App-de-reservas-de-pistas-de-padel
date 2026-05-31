package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.AsignacionService;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.HistorialCuponesService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionCupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionTiempoReserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservaController {
	
	private final HistorialCuponesService historialCuponesService;
	private final ReservaService reservaService;
	private final PistaService pistaService;
	private final AsignacionService asignacionService;
	private final UsuarioService usuarioService;

		
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/reservar/submit")
	public String procesarReserva(
	        @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
	        @RequestParam("horaEntrada") String horaEntradaStr,
	        @RequestParam("horaSalida") String horaSalidaStr,
	        @RequestParam("numeroPista") long numeroPista,
	        @RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz,
	        @RequestParam(value = "cantRaquetas", defaultValue = "0") int cantRaquetas,
	        @RequestParam(value = "codigoCupon", required = false) String codigoCupon,
	        @AuthenticationPrincipal UserDetails uLogueado,
	        Model model) {

	    LocalTime horaEntrada = LocalTime.parse(horaEntradaStr);
	    LocalTime horaSalida = LocalTime.parse(horaSalidaStr);

	    Optional<Pista> pOpt = pistaService.buscarPorId(numeroPista);
	    Optional<Usuario> uOpt = usuarioService.buscarPorNombre(uLogueado.getUsername());

	    Pista p;
	    Usuario u;
	    Reserva r;
	    
	    double precioTotal;
	    
	    if (pOpt.isEmpty() || uOpt.isEmpty()) {

	    	return "redirect:/home";

	    }
	    	
	    p = pOpt.get();
	    u = uOpt.get();

	    if (horaEntrada.isBefore(LocalTime.now()) && fecha.getDayOfYear() == LocalDate.now().getDayOfYear()) {
	    	
	        throw new ExcepcionTiempoReserva("No se puede reservar la pista para hoy si la hora de entrada no es posterior a la actual");
	        
	    }

	    if (horaEntrada.isAfter(horaSalida)) {
	    	
	        throw new ExcepcionTiempoReserva("No se puede reservar la pista. La hora de salida debe ser posterior a la de entrada");
	        
	    }

	    if (reservaService.tieneConflictoHorario(p.getNumero(), fecha, horaEntrada, horaSalida)) {
	    	
	        throw new ExcepcionTiempoReserva("La pista ya se encuentra reservada en el horario seleccionado.");
	        
	    }

	    precioTotal = reservaService.calcularPrecioTotal(horaEntrada, horaSalida, cantRaquetas, p.getPrecioHora(), usaLuz);

	    r = Reserva.builder()
	            .fecha(fecha)
	            .horaEntrada(horaEntrada)
	            .horaSalida(horaSalida)
	            .precioTotal(precioTotal)
	            .usuario(u)
	            .asignaciones(new ArrayList<>())
	            .build();

	    if (codigoCupon != null && !codigoCupon.isBlank()) {
	    	
	        try {
	        	
	            historialCuponesService.aplicarCuponAReserva(r, codigoCupon, u);
	            
	        } catch (ExcepcionCupon e) {
	        	
	            model.addAttribute("errorCupon", e.getMessage());
	            model.addAttribute("pista", p);
	            
	            return "detallesPista";
	        }
	    }

	    reservaService.crearReserva(r, u);
	    asignacionService.registrarAsignacionCompleta(r, p, usaLuz, cantRaquetas, r.getPrecioTotal());

	    model.addAttribute("reserva", r);
	    model.addAttribute("pista", p);
	    model.addAttribute("usuario", uLogueado);
	    model.addAttribute("usaLuz", usaLuz);
	    model.addAttribute("cantRaquetas", cantRaquetas);
	    model.addAttribute("precioTotal", r.getPrecioTotal());
	    model.addAttribute("precioLuz", reservaService.calcularPrecioLuz(horaEntrada, horaSalida, usaLuz));
	    model.addAttribute("precioPalas", reservaService.calcularPrecioPalas(cantRaquetas));
	    model.addAttribute("costoPista", p.getPrecioHora() * reservaService.calcularHorasTotales(horaEntrada, horaSalida));

	    return "reservaTicket";
	    
	}
	
}
