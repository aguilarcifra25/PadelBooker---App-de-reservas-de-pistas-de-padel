package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.UsuarioRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base.ServiciosBaseImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService extends ServiciosBaseImpl<Usuario, Long, UsuarioRepository>{

	private final UsuarioRepository usuarioRepository;
			
	public Optional<Usuario> buscarPorNombre(String username) {
		
        return usuarioRepository.findByUsername(username);
        
    }
	
	public List<Usuario> buscarPorRol (RolUsuario rol) {
		
		return usuarioRepository.findByRolUsuario(rol);
		
	}
		
	
	public List<Usuario> buscarTodosMenosAdminYLogeado (String username) {
		
		return usuarioRepository.findAllExceptYouAndAdmin(username);
		
	}
	
	public Integer contarUsuariosNormales (RolUsuario rol) {
		
		return usuarioRepository.countByRolUsuario(rol);
		
	}
	
}
