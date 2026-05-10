package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	public List<Usuario> getUsuarios () {
		
		return usuarioRepository.findAll();
		
	}
	
}
