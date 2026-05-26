package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerPrincipal {
	
	@GetMapping({"/","/home"})
	public String paginaPrincipal (Model model) {
			    		
		return "home";
	}

<<<<<<< HEAD
	@PostMapping("/editarUsuario/submit")
	public String procesarEdicionUsuario(@Valid @ModelAttribute("usuario") Usuario u,BindingResult bindingResult, 
												Model model, @AuthenticationPrincipal UserDetails usuario) {
	    
	    if (bindingResult.hasErrors() && usuario.getAuthorities().stream()
	    														.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
	    	
	        return "admin/editarUsuario";
	        
	    } else if (bindingResult.hasErrors() && usuario.getAuthorities().stream()
	    																.anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
			
			return "perfil";
			
		}
	    
	    u.setPassword(encoder.encode(u.getPassword()));
	    usuarioService.editar(u);
	    	    
	    if ( usuario.getAuthorities().stream()
	    							.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
	    	
	    	 return "redirect:/panelAdmin";
	    	
	    } else {
	    	
	    	model.addAttribute("usuario", usuarioService.buscarPorNombre(usuario.getUsername()).get());
	    	
	    	return "redirect:/perfil";
	    	
	    }
	    
	   
	}
	
	
	@GetMapping("/crearUsuario")
	public String crearUsuario (Model model) {
			    		
		Usuario nuevoUsuario = new Usuario();
	    	    
	    nuevoUsuario.setRolUsuario(RolUsuario.USER); 
	    
	    model.addAttribute("usuario", nuevoUsuario);
		
		return "crearUsuario";
	}
	
	@PostMapping("/crearUsuario/submit")
	public String procesarCreacionUsuario(@Valid @ModelAttribute("usuario") Usuario u,
										BindingResult bindingResult, @AuthenticationPrincipal UserDetails uLogueado, Model model) {
		
		if (bindingResult.hasErrors()) {
	       
	        return "crearUsuario";
	        
	    }
		
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
	public String procesarEdicionPista(@Valid @ModelAttribute("pista") Pista p,
	        								BindingResult bindingResult,Model model) {
	    
	    if (bindingResult.hasErrors()) {
	    	
	        return "admin/editarPista";
	        
	    }
	    
	    pistaService.editar(p);
	    
	    return "redirect:/panelAdmin";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/crearPista")
	public String crearPista(Model model) {
	    
	    model.addAttribute("pista", new Pista());
	    
	    return "admin/crearPista";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/crearPista/submit")
	public String procesarCreacionPista(@Valid @ModelAttribute("pista") Pista p, BindingResult bindingResult,
	        								Model model) {
	            
	    if (bindingResult.hasErrors()) {
	    	
	        return "admin/crearPista";
	        
	    }
	            
	    pistaService.guardar(p);
	    
	    return "redirect:/panelAdmin";
	}
		
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrarPista/{numero}")
	public String borrarPista(@PathVariable("numero") long numero) {
		
		Optional<Pista> pBorrar = pistaService.buscarPorId(numero);	
		
		boolean asignada = asignacionRepository.findAll().stream()
										.anyMatch(a -> a.getPista() == pBorrar.get());
		
		if (asignada) {
			
			throw new ExcepcionPistaOcupada("No se puede borrar la pista. Debe eliminar las reservas asociadas a ellas y avisar a los usuarios.");
			
		}
		
		if (pBorrar.isPresent()) {
			
			pistaService.borrar(pBorrar.get());
			
		}
		
		return "redirect:/panelAdmin";		
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/crearReserva")
	public String crearReserva(Model model) {
	    
	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
	    
	    return "admin/crearReserva";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/reserva/crear")
	public String guardarReserva( @RequestParam("fecha") @NotNull @FutureOrPresent @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
									@RequestParam("horaInicio") @NotNull LocalTime horaEntrada, @RequestParam("horaFin") @NotNull LocalTime horaSalida,        
									@RequestParam("numero") @NotNull Long numero, @RequestParam("usuarioId") @NotNull Long usuarioId,
									@RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz, @RequestParam("cantRaquetas") @Min(0) @Max(4) int cantRaquetas,
									@RequestParam(value = "observaciones", required = false) String observaciones, Model model) {
	   
	    Pista p = pistaService.buscarPorId(numero).get();
	    Usuario u = usuarioService.buscarPorId(usuarioId).get();
	    
	    double precioReserva = reservaService.calcularPrecioTotal(
	            horaEntrada, 
	            horaSalida, 
	            cantRaquetas, 
	            p.getPrecioHora(),
	            usaLuz
	        );
	    
	    Reserva r = Reserva.builder()
	            .fecha(fecha)
	            .horaEntrada(horaEntrada)
	            .horaSalida(horaSalida)
	            .precioTotal(precioReserva)
	            .usuario(u)
	            .asignaciones(new ArrayList<>())
	            .build();
	    
	    AsignacionPK aPK = new AsignacionPK();
	    aPK.setPista_id(p.getNumero());
	    
	    Asignacion a = Asignacion.builder()
	            .asignacionPK(aPK)
	            .usaLuz(usaLuz)
	            .cantRaquetas(cantRaquetas)
	            .precio(p.getPrecioHora())
	            .observaciones(observaciones)
	            .build();
	            
	    a.agregarEnReserva(r);
	    a.agregarEnPista(p);
	            
	    reservaService.guardar(r);
	    
	    return "redirect:/panelAdmin";
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
	public String procesarEdicionReserva(@ModelAttribute("reserva") Reserva r, @RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz,
											@RequestParam("cantRaquetas") @Min(0) @Max(4) int cantRaquetas) {
	    
	    Reserva reservaExistente = reservaService.buscarPorId(r.getCodigo()).get();
	    
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
=======
}
>>>>>>> develop
