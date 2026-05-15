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

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
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
	
	
	
	@GetMapping("/borrarUsuario/{id}")
	public String borrarUsuario(@PathVariable("id") long id) {
		
		Optional<Usuario> uBorrar = usuarioService.buscarPorId(id);	
		
		if (uBorrar.isPresent()) {
			
			usuarioService.borrar(uBorrar.get());
			
		} 
		
		return "redirect:/panelAdmin";		
	}
		
	
	@GetMapping("/editarUsuario/{id}")
	public String mostrarFormularioEdicionUsuario(@PathVariable("id") long id, Model model) {
						 		
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
		
		return "redirect:/panelAdmin";
		
	}
	
	
	@GetMapping("/crearUsuario")
	public String crearUsuario (Model model) {
			    		
		return "crearUsuario";
	}
	
	@PostMapping("/crearUsuario/submit")
	public String procesarCreacionUsuario(@ModelAttribute("usuario") Usuario u) {
		
		u.setPassword(encoder.encode(u.getPassword()));
		
		usuarioService.guardar(u);
		
		return "redirect:/panelAdmin";
		
	}
	
	
	
	@GetMapping("/editarPista/{numero}")
	public String mostrarFormularioEdicionPista(@PathVariable("numero") long numero, Model model) {
						 		
		Optional<Pista> pEditar = pistaService.buscarPorId(numero);
		
		if (pEditar.isPresent()) {
			
			model.addAttribute("pista", pEditar.get());
			
			return "admin/editarPista";
			
		} else {
			
			return "redirect:/panelAdmin";
			
		}			
	}
	
	@PostMapping("/editarPista/submit")
	public String procesarEdicionPista(@ModelAttribute("pista") Pista p) {
		
		pistaService.editar(p);
		
		return "redirect:/panelAdmin";
		
	}
	
	
	@GetMapping("/crearPista")
	public String crearPista (Model model) {
			    		
		return "admin/crearPista";
	}
	
	@PostMapping("/crearPista/submit")
	public String procesarCreacionPista(@ModelAttribute("pista") Pista p) {
				
		pistaService.guardar(p);
		
		return "redirect:/panelAdmin";
		
	}
	
	
	@GetMapping("/borrarPista/{numero}")
	public String borrarPista(@PathVariable("numero") long numero) {
		
		Optional<Pista> pBorrar = pistaService.buscarPorId(numero);	
		System.out.println(pBorrar);
		if (pBorrar.isPresent()) {
			
			pistaService.borrar(pBorrar.get());
			
		} 
		
		return "redirect:/panelAdmin";		
	}
	
}
