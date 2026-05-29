package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerPrincipal {
	
	@GetMapping({"/","/home"})
	public String paginaPrincipal (Model model) {
			    		
		return "home";
	}

	@GetMapping("/nosotros")
	public String paginaInfo () {
			    		
		return "nosotros";
	}
	
}
