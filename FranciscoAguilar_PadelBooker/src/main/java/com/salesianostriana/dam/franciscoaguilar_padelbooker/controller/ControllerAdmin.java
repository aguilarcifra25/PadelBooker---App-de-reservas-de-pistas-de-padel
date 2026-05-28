package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.AsignacionService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionEdicionEmailRepetido;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionEdicionNombreRepetido;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionNombreRepetido;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionPistaOcupada;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionTiempoReserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Asignacion;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.AsignacionPK;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerAdmin {

	private final PistaService pistaService;
	private final UsuarioService usuarioService;
	private final ReservaService reservaService;
	private final AsignacionService asignacionService;
	private final PasswordEncoder encoder;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/panelAdmin")
	public String paginaAdmin (Model model, @AuthenticationPrincipal UserDetails usuario) {
				
	    model.addAttribute("listaUsuarios", usuarioService.buscarTodosMenosAdminYLogeado(usuario.getUsername()));
	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    model.addAttribute("listaReservas", reservaService.buscarTodos());
	    
	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
	    	    
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
	public String procesarEdicionUsuario(@Valid @ModelAttribute("usuario") Usuario u,BindingResult bindingResult, 
											Model model, @AuthenticationPrincipal UserDetails uLogueado) {
	    
		if (usuarioService.buscarTodos().stream()
				.anyMatch(user -> user.getUsername().equals(u.getUsername()) 
						&& !user.getId().equals(u.getId()))) {

			throw new ExcepcionEdicionNombreRepetido("El nombre de usuario que intenta seleccionar está en uso.");

		} else if (usuarioService.buscarTodos().stream()
				.anyMatch(user -> user.getEmail().equals(u.getEmail()) 
						&& !user.getId().equals(u.getId()))) {
			
			throw new ExcepcionEdicionEmailRepetido("El email que intenta seleccionar está en uso.");
			
		}
		
		
	    if (bindingResult.hasErrors()) {
	    	
	        return "admin/editarUsuario";
	        
	    }
	    
	    usuarioService.editar(u);
	    
	    if (uLogueado.getAuthorities().stream()
	    							.anyMatch(rol -> rol.getAuthority().equals("ROLE_ADMIN"))) {
	    	
	    	return "redirect:/panelAdmin";
	    	
	    } else {
	    	
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
		
		if (usuarioService.buscarTodos().stream()
										.anyMatch(user -> user.getUsername().equals(u.getUsername()))) {
	    	
	    	throw new ExcepcionNombreRepetido("El nombre de usuario que intenta seleccionar está en uso.");
	    		    	
	    } else if (usuarioService.buscarTodos().stream()
				.anyMatch(user -> user.getEmail().equals(u.getEmail()))) {

			throw new ExcepcionNombreRepetido("El correo que intenta seleccionar está en uso.");

		}
		
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
		
		boolean asignada = asignacionService.buscarTodos().stream()
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
	    model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(RolUsuario.USER));
	    
	    return "admin/crearReserva";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/reserva/crear")
	public String guardarReserva( @RequestParam("fecha") @NotNull @FutureOrPresent @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
									@RequestParam("horaInicio") @NotNull LocalTime horaEntrada, @RequestParam("horaFin") @NotNull LocalTime horaSalida,        
									@RequestParam("numero") @NotNull Long numero, @RequestParam("usuarioId") @NotNull Long usuarioId,
									@RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz, @RequestParam("cantRaquetas") @Min(0) @Max(4) int cantRaquetas,
									@RequestParam(value = "observaciones", required = false) String observaciones, Model model) {
	   
		boolean ocupada;
		
		if (horaEntrada.isBefore(LocalTime.now()) && fecha.getDayOfYear() == LocalDate.now().getDayOfYear()) {
			
			throw new ExcepcionTiempoReserva("No se puede reservar la pista para hoy si la hora de entrada no es posterior a la actual");			
			
		}
		
		if (horaEntrada.isAfter(horaSalida)) {
		    	
			throw new ExcepcionTiempoReserva("No se puede reservar la pista. La hora de salida debe ser posterior a la de entrada");
		    		    	
		}
		
		ocupada = reservaService.tieneConflictoHorario(numero, fecha, horaEntrada, horaSalida);
	    
	    if (ocupada) {
	    	
	        throw new ExcepcionTiempoReserva("La pista ya se encuentra reservada en el horario seleccionado.");
	        
	    }
		
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
	    
		boolean ocupada;
		
		Long numero = reservaService.buscarPorId(r.getCodigo()).get().getAsignaciones().getFirst().getPista().getNumero();
		
		ocupada = reservaService.tieneConflictoHorario(numero, r.getFecha(), r.getHoraEntrada(), r.getHoraSalida());
		
		if (r.getHoraEntrada().isBefore(LocalTime.now()) && r.getFecha().getDayOfYear() == LocalDate.now().getDayOfYear()) {
			
			throw new ExcepcionTiempoReserva("No se puede reservar la pista para hoy si la hora de entrada no es posterior a la actual");			
			
		}
		
		
		if (ocupada) {
	    	
	        throw new ExcepcionTiempoReserva("La pista ya se encuentra reservada en el horario seleccionado.");
	        
	    }
		
		if (r.getHoraEntrada().isAfter(r.getHoraSalida())) {
	    	
			throw new ExcepcionTiempoReserva("No se puede reservar la pista. La hora de salida debe ser posterior a la de entrada");
		    		    	
		}	
		
		
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
	
	
	@GetMapping("/borrarReserva/{codigo}")
	public String borrarReserva(@PathVariable("codigo") long codigo, @AuthenticationPrincipal UserDetails uLogueado) {
		
		Optional<Reserva> rBorrar = reservaService.buscarPorId(codigo);	
		
		if (rBorrar.isPresent()) {
			
			reservaService.borrar(rBorrar.get());
			
		} 
		
		if (uLogueado != null && uLogueado.getAuthorities().stream()
				.filter(rol -> rol.getAuthority()
				.equals("ROLE_ADMIN"))
				.findFirst()
				.isPresent())  {
			
			return "redirect:/panelAdmin";
						
		} else {
			
			return "redirect:/perfil";
			
		}
		
		
				
	}
	
	
}
