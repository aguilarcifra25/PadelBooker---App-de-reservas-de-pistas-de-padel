package com.salesianostriana.dam.franciscoaguilar_padelbooker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByUsername(String nombreUsuario);
	
}
