package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;
		
	@GetMapping("/perfil")
	public String paginaMiPerfil (Model model, @AuthenticationPrincipal UserDetails usuario) {
				
		Optional<Usuario> u = usuarioService.buscarPorNombre(usuario.getUsername());
				
	    model.addAttribute("usuario", u.get());
	    	
		return "perfil";
	}
	
	
	
	
	
}
