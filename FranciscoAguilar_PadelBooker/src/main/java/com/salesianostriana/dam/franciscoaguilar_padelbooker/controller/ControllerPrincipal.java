package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Asignacion;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerPrincipal {

	private final ReservaService reservaService;
	private final UsuarioService usuarioService;
	private final PistaService pistaService;
	private final PasswordEncoder encoder;
	
	
	@GetMapping({"/","/home"})
	public String paginaPrincipal (Model model) {
			    		
		return "home";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/panelAdmin")
	public String paginaAdmin (Model model, @AuthenticationPrincipal UserDetails usuario) {
				
	    model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    model.addAttribute("listaReservas", reservaService.buscarTodos());
	    	    
		return "admin/panelAdmin";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
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
	public String procesarEdicionUsuario(@ModelAttribute("usuario") Usuario u) {
		
		usuarioService.editar(u);
		
		return "redirect:/panelAdmin";
		
	}
	
	
	@GetMapping("/crearUsuario")
	public String crearUsuario (Model model) {
			    		
		return "crearUsuario";
	}
	
	@PostMapping("/crearUsuario/submit")
	public String procesarCreacionUsuario(@ModelAttribute("usuario") Usuario u, @AuthenticationPrincipal UserDetails uLogueado) {
		
		u.setPassword(encoder.encode(u.getPassword()));
		
		usuarioService.guardar(u);
		
		if (uLogueado != null && uLogueado.getAuthorities().stream()
						.filter(rol -> rol.getAuthority()
						.equals("ROLE_ADMIN"))
						.findFirst()
						.isPresent())  {
			
			return "redirect:/panelAdmin";
			
		}
		
		return "redirect:/login";
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/editarPista/submit")
	public String procesarEdicionPista(@ModelAttribute("pista") Pista p) {
		
		pistaService.editar(p);
		
		return "redirect:/panelAdmin";
		
	}	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/crearPista")
	public String crearPista (Model model) {
			    		
		return "admin/crearPista";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/crearPista/submit")
	public String procesarCreacionPista(@ModelAttribute("pista") Pista p) {
				
		pistaService.guardar(p);
		
		return "redirect:/panelAdmin";
		
	}
		
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrarPista/{numero}")
	public String borrarPista(@PathVariable("numero") long numero) {
		
		Optional<Pista> pBorrar = pistaService.buscarPorId(numero);	
		
		if (pBorrar.isPresent()) {
			
			pistaService.borrar(pBorrar.get());
			
		} 
		
		return "redirect:/panelAdmin";		
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/crearReserva")
	public String crearReserva (Model model) {
			    		
		return "admin/crearReserva";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/editarReserva/{codigo}")
	public String mostrarFormularioEdicionReserva(@PathVariable("codigo") long codigo, Model model) {
						 		
		Optional<Reserva> rEditar = reservaService.buscarPorId(codigo);
		
		if (rEditar.isPresent()) {
			
			model.addAttribute("reserva", rEditar.get());
			
			return "admin/editarReserva";
			
		} else {
			
			return "redirect:/panelAdmin";
			
		}			
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/editarReserva/submit")
	public String procesarEdicionReserva(
	        @ModelAttribute("reserva") Reserva r,
	        @RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz,
	        @RequestParam("cantRaquetas") int cantRaquetas) {
	    
		Reserva reservaExistente = reservaService.buscarPorId(r.getCodigo())
												.orElseThrow(() -> new RuntimeException("No se encuentra la reserva " + r.getCodigo()));
	    
	    reservaExistente.setFecha(r.getFecha());
	    reservaExistente.setHoraEntrada(r.getHoraEntrada());
	    reservaExistente.setHoraSalida(r.getHoraSalida());

	    Asignacion asignacionExistente = reservaExistente.getAsignaciones().getFirst();
	    
	    asignacionExistente.setUsaLuz(usaLuz);
	    asignacionExistente.setCantRaquetas(cantRaquetas);
	    
	    double precioReservaExistente = reservaService.calcularPrecioTotal(
	        reservaExistente.getHoraEntrada(), 
	        reservaExistente.getHoraSalida(), 
	        asignacionExistente.getCantRaquetas(), 
	        asignacionExistente.getPrecio(),
	        asignacionExistente.isUsaLuz()
	    );
	    
	    reservaExistente.setPrecioTotal(precioReservaExistente);
	    
	    reservaService.editar(reservaExistente);
	    
	    return "redirect:/panelAdmin?tab=reservas";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrarReserva/{codigo}")
	public String borrarReserva(@PathVariable("codigo") long codigo) {
		
		Optional<Reserva> rBorrar = reservaService.buscarPorId(codigo);	
		
		if (rBorrar.isPresent()) {
			
			reservaService.borrar(rBorrar.get());
			
		} 
		
		return "redirect:/panelAdmin";		
	}
	
}
