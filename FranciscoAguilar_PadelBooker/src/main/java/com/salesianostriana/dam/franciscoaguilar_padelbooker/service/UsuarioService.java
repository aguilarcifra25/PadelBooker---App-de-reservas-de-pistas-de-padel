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
	
	public List<Usuario> buscarConFiltros(String logueado, String nombre, String email, String rol) {
		
	    String n = null;
	    String e = null;
	    RolUsuario r = null;

	    if (nombre != null && !nombre.isBlank()) {
	    	
	    	n = nombre.trim();
	    	
	    }
	    
	    if (email != null && !email.isBlank()) {
	    	
	    	e = email.trim();
	    	
	    }
	    
	    
	    if (rol != null && !rol.isBlank()) {
	       
	    	r = RolUsuario.valueOf(rol.trim());
	       	        
	    }

	    if (r != null) {
	    	
	        return usuarioRepository.buscarConFiltrosConRol(logueado, n, e, r);
	        
	    } else {
	    	
	        return usuarioRepository.buscarConFiltrosSinRol(logueado, n, e);
	    }
	}
	
	public boolean comprobarRol (String username, RolUsuario rol) {
		
		return usuarioRepository.existsByUsernameAndRolUsuario(username, rol);
		
	}
	
	
	public boolean comprobarUsernameEnOtro (String username, Long id) {
		
		return usuarioRepository.existsByUsernameAndIdNot(username, id);
		
	}
	
	
	public boolean comprobarEmailEnOtro (String email, Long id) {
		
		return usuarioRepository.existsByUsernameAndIdNot(email, id);
		
	}
}
