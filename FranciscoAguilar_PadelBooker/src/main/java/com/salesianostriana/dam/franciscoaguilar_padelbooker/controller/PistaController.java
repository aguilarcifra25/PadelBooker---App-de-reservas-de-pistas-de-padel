package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PistaController {

	private final PistaService pistaService;
	private final ReservaService reservaService;
			
	@GetMapping("/pistas")
	public String verPistas (Model model) {
		
		model.addAttribute("listaPistas", pistaService.buscarTodos());	
		
		return "pistas";
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/detallesPista/{numero}")
	public String verDetallesPista(@PathVariable("numero") long numero, Model model) {

	    Optional<Pista> pista = pistaService.buscarPorId(numero);

	    if (pista.isPresent()) {

	        // -- Reservas de esta pista con fecha y horas --
	        List<Map<String, String>> reservasPista = reservaService.buscarTodos().stream()
	                .flatMap(r -> r.getAsignaciones().stream()
	                        .filter(a -> a.getPista().getNumero().equals(numero))
	                        .map(a -> Map.of(
	                                "fecha", r.getFecha().toString(),
	                                "entrada", r.getHoraEntrada().toString(),
	                                "salida", r.getHoraSalida().toString()
	                        )))
	                .toList();

	        model.addAttribute("reserva", new Reserva());
	        model.addAttribute("pista", pista.get());
	        model.addAttribute("reservasPista", reservasPista);

	        return "detallesPista";

	    } else {

	        return "redirect:/pistas";

	    }
	}
	
}
