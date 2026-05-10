package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PistaController {

	private final PistaService pistaService;
			
	@GetMapping("/pistas")
	public String paginaPrincipal (Model model) {
		
		model.addAttribute("listaPistas", pistaService.getPistas());	
		
		return "pistas";
	}
	
}
