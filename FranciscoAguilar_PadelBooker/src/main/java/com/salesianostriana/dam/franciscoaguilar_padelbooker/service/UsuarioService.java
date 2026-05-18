package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.UsuarioRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base.ServiciosBaseImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService extends ServiciosBaseImpl<Usuario, Long, UsuarioRepository>{

	private final UsuarioRepository usuarioRepository;
			
	public Optional<Usuario> buscarPorNombre(String username) {
		
        return usuarioRepository.findByUsername(username);
        
    }
	
}
