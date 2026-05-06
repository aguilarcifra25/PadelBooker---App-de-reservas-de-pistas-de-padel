package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import org.springframework.stereotype.Controller;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservaController {

	private final ReservaService reservaService;
	
}
