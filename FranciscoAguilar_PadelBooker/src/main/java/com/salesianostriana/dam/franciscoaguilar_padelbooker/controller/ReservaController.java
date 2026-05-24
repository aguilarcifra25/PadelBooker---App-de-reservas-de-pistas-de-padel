package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.AsignacionService;

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
	
	private final ReservaService reservaService;
	private final PistaService pistaService;
	private final AsignacionService asignacionService;
	private final UsuarioService usuarioService;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/reservar/submit")
	public String procesarReserva(
			@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date fecha,
	        @RequestParam("horaEntrada") String horaEntradaStr,
	        @RequestParam("horaSalida") String horaSalidaStr,
	        @RequestParam("numeroPista") long numeroPista,
	        @RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz,
	        @RequestParam(value = "cantRaquetas", defaultValue = "0") int cantRaquetas,
	        @AuthenticationPrincipal UserDetails uLogueado,
	        Model model) {
	    
	    Optional<Pista> pOpt = pistaService.buscarPorId(numeroPista);
	    Optional<Usuario> uOpt = usuarioService.buscarPorNombre(uLogueado.getUsername());
	    
	    Reserva reserva;
	    
	    double precioTotal;
	    
	    if (pOpt.isEmpty()) {
	    	
	        return "redirect:/home";
	        
	    } else if (uOpt.isEmpty()) {
	    	
	    	return "redirect:/home";
	    	
	    }         
                
	    LocalTime horaEntrada = LocalTime.parse(horaEntradaStr);
	    LocalTime horaSalida = LocalTime.parse(horaSalidaStr);
	    
	    Pista p = pOpt.get();
	    
	    precioTotal = reservaService.calcularPrecioTotal(horaEntrada, horaSalida, cantRaquetas, p.getPrecioHora(), usaLuz);

	    reserva = Reserva.builder()
	            .fecha(fecha)
	            .horaEntrada(horaEntrada)
	            .horaSalida(horaSalida)
	            .precioTotal(precioTotal)
	            .usuario(uOpt.get())
	            .asignaciones(new ArrayList<>())
	            .build();
	    
	    reservaService.guardar(reserva);
	    	    
	    asignacionService.registrarAsignacionCompleta(reserva, p, usaLuz, cantRaquetas, precioTotal);
	    
	    
	    model.addAttribute("reserva", reserva);
	    model.addAttribute("pista", p);
	    model.addAttribute("usuario", uLogueado);
	    model.addAttribute("usaLuz", usaLuz);
	    model.addAttribute("cantRaquetas", cantRaquetas);
	    model.addAttribute("precioTotal", precioTotal);

	    return "reservaTicket"; 
	}
	
}
