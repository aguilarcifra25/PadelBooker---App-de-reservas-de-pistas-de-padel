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
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerPrincipal {

	private final UsuarioService usuarioService;
	private final PistaService pistaService;
	private final PasswordEncoder encoder;
	
	
	@GetMapping("/panelAdmin")
	public String paginaAdmin (Model model, @AuthenticationPrincipal UserDetails usuario) {
				
	    model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    	    
		return "admin/panelAdmin";
	}
	
	
	
	@GetMapping("/borrar/{id}")
	public String borrar(@PathVariable("id") long id) {
		
		Optional<Usuario> aBorrar = usuarioService.buscarPorId(id);	
		
		if (aBorrar.isPresent()) {
			
			usuarioService.borrar(aBorrar.get());
			
		} 
		
		return "redirect:/panelAdmin";		
	}
	
	
	
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEdicion(@PathVariable("id") long id, Model model) {
						 		
		Optional<Usuario> uEditar = usuarioService.buscarPorId(id);
		
		if (uEditar.isPresent()) {
			
			model.addAttribute("usuario", uEditar.get());
			
			return "admin/editarUsuario";
			
		} else {
			
			return "redirect:/panelAdmin";
			
		}			
	}
	
	@PostMapping("/editarUsuario/submit")
	public String procesarEdicionUsuario(@ModelAttribute("alumno") Usuario u) {
		
		usuarioService.editar(u);	
		System.out.println(u);
		
		return "redirect:/panelAdmin";
		
	}
	
	
	@GetMapping("/crearUsuario")
	public String crearUsuario (Model model) {
			    		
		return "crearUsuario";
	}
	
	@PostMapping("/crearUsuario/submit")
	public String procesarCreacionUsuario(@ModelAttribute("alumno") Usuario u) {
		
		u.setPassword(encoder.encode(u.getPassword()));
		
		usuarioService.guardar(u);
		
		return "redirect:/panelAdmin";
		
	}
	
}
