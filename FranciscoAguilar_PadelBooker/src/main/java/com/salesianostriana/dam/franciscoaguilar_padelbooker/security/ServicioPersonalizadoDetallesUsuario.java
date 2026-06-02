package com.salesianostriana.dam.franciscoaguilar_padelbooker.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioPersonalizadoDetallesUsuario implements UserDetailsService{
		 
		
		private final UsuarioRepository usuarioRepository;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			return usuarioRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		}
}
