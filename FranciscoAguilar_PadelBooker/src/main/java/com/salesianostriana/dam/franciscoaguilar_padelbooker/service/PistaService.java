package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.PistaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PistaService {

	private final PistaRepository pistaRepository;	
	
	public List<Pista> getPistas() {
		
		return pistaRepository.findAll();
		
	}
	
}
