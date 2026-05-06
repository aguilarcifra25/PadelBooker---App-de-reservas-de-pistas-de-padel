package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.PistaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PistaService {

	private final PistaRepository pistaRepository;	
	
}
