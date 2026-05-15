package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	@GetMapping({"/","/home"})
	public String paginaPrincipal (Model model) {
			    		
		return "home";
	}
	
	
	
	@GetMapping("/perfil")
	public String paginaMiPerfil (Model model, @AuthenticationPrincipal UserDetails usuario) {
				
	    model.addAttribute("nombreUsuario", usuario.getUsername());
	    	
		return "perfil";
	}
	
	
	
	
	
}
