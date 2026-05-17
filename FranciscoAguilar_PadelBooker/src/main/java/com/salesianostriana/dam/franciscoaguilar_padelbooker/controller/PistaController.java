package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PistaController {

	private final PistaService pistaService;
			
	@GetMapping("/pistas")
	public String verPistas (Model model) {
		
		model.addAttribute("listaPistas", pistaService.buscarTodos());	
		
		return "pistas";
	}
	
	
	@GetMapping("/detallesPista/{numero}")
	public String verDetallesPista (@PathVariable("numero") long numero, Model model) {
		
		Optional<Pista> pista = pistaService.buscarPorId(numero);
		

		if (pista.isPresent()) {
			
			model.addAttribute("pista", pista.get());
			
			return "detallesPista";
			
		} else {
			
			return "redirect:/pistas";
			
		}	
	}
	
}
