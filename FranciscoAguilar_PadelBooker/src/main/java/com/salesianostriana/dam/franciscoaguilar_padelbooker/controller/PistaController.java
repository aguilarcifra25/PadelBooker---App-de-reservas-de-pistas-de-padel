package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import org.springframework.stereotype.Controller;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PistaController {

	private final PistaService pistaService;
	
}
