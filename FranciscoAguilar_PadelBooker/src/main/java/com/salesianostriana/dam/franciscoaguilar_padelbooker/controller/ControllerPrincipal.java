package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerPrincipal {
	
	@GetMapping({"/","/home"})
	public String paginaPrincipal () {
			    		
		return "home";
		
	}

	@GetMapping("/nosotros")
	public String paginaInfo () {
			    		
		return "nosotros";
		
	}
	
}
