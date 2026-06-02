package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.AsignacionService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.CuponService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.HistorialCuponesService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionBorrarAdmin;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionCrearCupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionCupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionCuponAsignado;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionEdicionEmailRepetido;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionEdicionOtroUser;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionEdicionPropioAdmin;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionEdicionNombreRepetido;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionNombreRepetido;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionPistaOcupada;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionTiempoReserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Asignacion;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.AsignacionPK;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Cupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.HistorialCupones;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerAdmin {

	private final HistorialCuponesService historialCuponesService;
	private final PistaService pistaService;
	private final UsuarioService usuarioService;
	private final ReservaService reservaService;
	private final AsignacionService asignacionService;
	private final CuponService cuponService;
	private final PasswordEncoder encoder;


	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/panelAdmin")
	public String paginaAdmin( Model model, @AuthenticationPrincipal UserDetails usuario,
	        @RequestParam(required = false) String usuarioFiltrarReserva,
	        @RequestParam(required = false) String fechaFiltrarReserva,
	        @RequestParam(required = false) String horaEntradaFiltrarReserva,
			@RequestParam(required = false) String nombreFiltrarUsuario,
			@RequestParam(required = false) String emailFiltrarUsuario,
			@RequestParam(required = false) String rolFiltrarUsuario) {
				
	    model.addAttribute("listaUsuarios", usuarioService.buscarTodosMenosAdminYLogeado(usuario.getUsername()));
	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    
	    // -- Filtrar usuarios --
	    
	    if ((nombreFiltrarUsuario != null && !nombreFiltrarUsuario.isBlank()) ||
	            (emailFiltrarUsuario != null && !emailFiltrarUsuario.isBlank()) ||
	            (rolFiltrarUsuario != null && !rolFiltrarUsuario.isBlank())) {

	            model.addAttribute("listaUsuarios", usuarioService.buscarConFiltros(usuario.getUsername(), nombreFiltrarUsuario, emailFiltrarUsuario, rolFiltrarUsuario));
	            
	        } else {
	        	
	        	
	            model.addAttribute("listaUsuarios", usuarioService.buscarTodosMenosAdminYLogeado(usuario.getUsername()));
	            
	        }
	    
	    
	    // -- Filtrar reservas --
	    
	    if ((usuarioFiltrarReserva != null && !usuarioFiltrarReserva.isBlank()) || 
	        (fechaFiltrarReserva != null && !fechaFiltrarReserva.isBlank()) || 
	        (horaEntradaFiltrarReserva != null && !horaEntradaFiltrarReserva.isBlank())) {
	        	        
	        model.addAttribute("listaReservas", reservaService.buscarConFiltros(usuarioFiltrarReserva, fechaFiltrarReserva, horaEntradaFiltrarReserva));
	        
	    } else {
	        
	        model.addAttribute("listaReservas", reservaService.buscarTodos());
	        
	    }
	    
	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
	    	    
	    model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
	            		.filter(c -> c.getUsuario() != null)
	            		.toList());
	    
	    return "admin/panelAdmin";
	}
	
	
	// ---- Usuarios ----	
	
	@GetMapping("/borrarUsuario/{id}")
	public String borrarUsuario(@PathVariable("id") long id, @AuthenticationPrincipal UserDetails uLogueado, HttpSession session) {
		
		Optional<Usuario> uBorrar = usuarioService.buscarPorId(id);	
		
		if (!uBorrar.isPresent()) {
			
			return "home";
			
		} 
		
		boolean esAdmin = uLogueado.getAuthorities().stream()
		        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		    
		boolean esSuPropiacuenta = uBorrar.get().getUsername().equals(uLogueado.getUsername());
				
		
	    if (!esAdmin && !esSuPropiacuenta) {
	    	
	        return "home";
	        
	    }
		
	    
	    if (esAdmin && uBorrar.get().getUsername().equals("admin")) {
	    	
	        throw new ExcepcionBorrarAdmin("No se puede borrar al admin por defecto.");
	        
	    }
	    
					
	    List<HistorialCupones> historialCompleto = historialCuponesService.buscarTodos();

	    List<HistorialCupones> historialUsuario = historialCompleto.stream()
	        .filter(historial -> historial.getUsuario() != null && historial.getUsuario().getId().equals(uBorrar.get().getId()))
	        .collect(Collectors.toList());
	    			
	    for (HistorialCupones historialCupones : historialUsuario) {
			
	    	historialCuponesService.borrar(historialCupones);
	    	
		}
	    
	    
		usuarioService.borrar(uBorrar.get());
					
		
		
		if (usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {
		
			return "redirect:/panelAdmin";		
		
		} else {
			
			//Destruye los datos de la sesión
			
			SecurityContextHolder.clearContext();
	        
	        if (session != null) {
	        	
	        
	            session.invalidate();
	        
	        }     
	            
	        return "redirect:/login?logout";
			
		}
	}
		
	
	@GetMapping("/editarUsuario/{id}")
	public String mostrarFormularioEdicionUsuario(@PathVariable("id") long id, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
	    
	    Optional<Usuario> uEditarOpt = usuarioService.buscarPorId(id);
	    Usuario uEditar;
	    
	    boolean esUser = usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.USER);
	    
	    if (uEditarOpt.isEmpty()) {
	    	
	    	if (esUser) {
	    		
	    		return "redirect:/perfil";
	    		
	    	} else {
	    	
	    		return "redirect:/panelAdmin"; 
	        
	    	}
	    }
	    
	    uEditar = uEditarOpt.get();
	    
	    if (!esUser && uEditar.getUsername().equals(uLogueado.getUsername())) {
	    	
	    	throw new ExcepcionEdicionPropioAdmin("Un admin no puede editar su propia cuenta.");
	    	
	    }  
	    	        
	                              
	    if (esUser && !uLogueado.getUsername().equals(uEditar.getUsername())) {
	    	
	        throw new ExcepcionEdicionOtroUser("No se pueden editar datos de un usuario que no es tuyo.");
	        
	    }
	    	   
	    model.addAttribute("usuario", uEditar);
	    
	    return "admin/editarUsuario";
	}

	@PostMapping("/editarUsuario/submit")
	public String procesarEdicionUsuario(@Valid @ModelAttribute("usuario") Usuario u,BindingResult bindingResult, 
											Model model, @AuthenticationPrincipal UserDetails uLogueado) {
	    
		if (usuarioService.comprobarUsernameEnOtro(u.getUsername(), u.getId())) {

			throw new ExcepcionEdicionNombreRepetido("El nombre de usuario que intenta seleccionar está en uso.");

		} else if (usuarioService.comprobarEmailEnOtro(u.getEmail(), u.getId())) {
			
			throw new ExcepcionEdicionEmailRepetido("El email que intenta seleccionar está en uso.");
			
		}	
		
		
	    if (bindingResult.hasErrors()) {
	    	
	        return "admin/editarUsuario";
	        
	    }
	    
	    u.setPassword(encoder.encode(u.getPassword()));
	    
	    if (u.getTelefono().equals("")) {
	    	
	    	u.setTelefono(null);
	    	
	    }
	    
	    usuarioService.editar(u);
	    
	    
	    if (!usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {
	        
	        UserDetails nuevoUsuarioAutenticado = org.springframework.security.core.userdetails.User
	                .withUsername(u.getUsername())
	                .password(u.getPassword())
	                .authorities(uLogueado.getAuthorities())
	                .build();

	        org.springframework.security.core.Authentication nuevaAutenticacion = 
	                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
	                        nuevoUsuarioAutenticado, 
	                        u.getPassword(), 
	                        uLogueado.getAuthorities()
	                );
	        
	        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(nuevaAutenticacion);
	    }
	    
	    
	    if (usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {
	    	
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
		
		if (usuarioService.comprobarUsernameEnOtro(u.getUsername(), u.getId())) {
	    	
	    	throw new ExcepcionNombreRepetido("El nombre de usuario que intenta seleccionar está en uso.");
	    		    	
	    } else if (usuarioService.comprobarEmailEnOtro(u.getEmail(), u.getId())) {

			throw new ExcepcionNombreRepetido("El correo que intenta seleccionar está en uso.");

		}
		
		if (bindingResult.hasErrors()) {
	       
	        return "crearUsuario";
	        
	    }
		
		
		
		
		if (uLogueado != null && usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.USER)) {
			
	        u.setRolUsuario(RolUsuario.USER);
	        
	    }
		
		u.setPassword(encoder.encode(u.getPassword()));
		
		usuarioService.guardar(u);
		
		if (uLogueado != null && usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {
			
			return "redirect:/panelAdmin";
			
		}
		
		return "redirect:/login";
		
	}
	
	
	// ---- Pistas ----
	
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
		
		if (!pBorrar.isPresent()) {
			
			return "home";
			
		}
		
		boolean asignada = asignacionService.buscarTodos().stream()
                .anyMatch(a -> a.getPista().getNumero().equals(pBorrar.get().getNumero()));
		
		if (asignada) {
			
			throw new ExcepcionPistaOcupada("No se puede borrar la pista. Debe eliminar las reservas asociadas a ellas y avisar a los usuarios.");
			
		}
				
			
		pistaService.borrar(pBorrar.get());
			
		
		return "redirect:/panelAdmin";		
	}
	
	
	
	// ---- Reservas ----
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/crearReserva")
	public String crearReserva(Model model) {
	     
	    // Crea una lista de los datos de las reservas para el js
	    List<Map<String, String>> todasReservas = reservaService.buscarTodos().stream()
	            .flatMap(r -> r.getAsignaciones().stream()
	                    .map(a -> Map.of(
	                            "fecha", r.getFecha().toString(),
	                            "entrada", r.getHoraEntrada().toString(),
	                            "salida", r.getHoraSalida().toString(),
	                            "pista", a.getPista().getNumero().toString()
	                    )))
	            .toList();

	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(RolUsuario.USER));
	    model.addAttribute("todasReservas", todasReservas);
	    
	    return "admin/crearReserva";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/reserva/crear")
	public String guardarReserva(
        @RequestParam("fecha") @NotNull @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
        @RequestParam("horaInicio") @NotNull LocalTime horaEntrada,
        @RequestParam("horaFin") @NotNull LocalTime horaSalida,
        @RequestParam("numeros") List<Long> numeros,
        @RequestParam("cantRaquetas") int cantPalas,
        @RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz,
        @RequestParam(value = "observaciones", required = false) String observaciones,
        @RequestParam("usuarioId") @NotNull Long usuarioId,
        @RequestParam(value = "codigoCupon", required = false) String codigoCupon,
        Model model) {

		Usuario u;
		Reserva r;
		Pista p;
		Asignacion a;
		AsignacionPK aPK;
		
		double precioTotal = 0.0;
		double precioPista;
		
		Optional<Usuario> uOpt = usuarioService.buscarPorId(usuarioId);
		
	    if (horaEntrada.isBefore(LocalTime.now()) && fecha.isEqual(LocalDate.now())) {
	    	
	        throw new ExcepcionTiempoReserva("No se puede reservar la pista para hoy si la hora de entrada no es posterior a la actual");
	        
	    }
	
	    if (!horaEntrada.isBefore(horaSalida)) {
	    	
	        throw new ExcepcionTiempoReserva("No se puede reservar la pista. La hora de salida debe ser posterior a la de entrada");
	        
	    }
	
	    for (Long numero : numeros) {
	    	
	        if (reservaService.tieneConflictoHorario(numero, fecha, horaEntrada, horaSalida)) {
	        	
	            throw new ExcepcionTiempoReserva("La pista " + numero + " ya se encuentra reservada en el horario seleccionado.");
	            
	        }
	    }
	    
	    
	    if (uOpt.isEmpty()) {
	    	
	    	return "redirect:/panelAdmin";
	    	
	    }
	    
	    u = usuarioService.buscarPorId(usuarioId).get();
	
	    r = Reserva.builder()
	            .fecha(fecha)
	            .horaEntrada(horaEntrada)
	            .horaSalida(horaSalida)
	            .precioTotal(0.0)
	            .usuario(u)
	            .asignaciones(new ArrayList<>())
	            .build();
	
	    
	    precioTotal += reservaService.calcularPrecioPalas(cantPalas);
	
	    
	    // -- Guarda cada asignacion con las pista en la misma reserva --
	    for (Long numero : numeros) {
	
	        p = pistaService.buscarPorId(numero).get();
	
	        precioPista = reservaService.calcularPrecioTotal(horaEntrada, horaSalida, 0, p.getPrecioHora(), usaLuz);
	        
	        precioTotal += precioPista;
	
	        aPK = new AsignacionPK();
	        aPK.setPista_id(p.getNumero());
	
	        a = Asignacion.builder()
	                .asignacionPK(aPK)
	                .usaLuz(usaLuz)
	                .cantPalas(cantPalas)
	                .precio(precioPista)
	                .observaciones(observaciones)
	                .build();
	
	        a.agregarEnReserva(r);
	        a.agregarEnPista(p);
	    }
	
	    r.setPrecioTotal(precioTotal);
	
	    // -- Aplicar cupón --
	    if (codigoCupon != null && !codigoCupon.isBlank()) {
	    	
	        try {
	        	
	            historialCuponesService.aplicarCuponAReserva(r, codigoCupon, u);
	            
	        } catch (IllegalArgumentException | ExcepcionCupon e) {
	        	
	            model.addAttribute("errorCupon", e.getMessage());
	            model.addAttribute("listaPistas", pistaService.buscarTodos());
	            model.addAttribute("listaUsuarios", usuarioService.buscarPorRol(RolUsuario.USER));
	            
	            return "admin/crearReserva";
	        }
	    }
	
	    reservaService.crearReserva(r, u);
	
	    return "redirect:/panelAdmin";
    
	}

	
		
	@GetMapping("/editarReserva/{codigo}")
	public String mostrarFormularioEdicionReserva(@PathVariable("codigo") long codigo, Model model, @AuthenticationPrincipal UserDetails uLogueado) {

	    Optional<Reserva> rEditarOpt = reservaService.buscarPorId(codigo);
	    boolean esUser = uLogueado.getAuthorities().stream().anyMatch(rol -> rol.getAuthority().equals("ROLE_USER"));
	    double porcentajeOriginal;
	    
	    if (rEditarOpt.isEmpty()) {
	    	
	        return esUser ? "redirect:/perfil" : "redirect:/panelAdmin";
	        
	    }

	    Reserva rEditar = rEditarOpt.get();

	    if (esUser && !uLogueado.getUsername().equals(rEditar.getUsuario().getUsername())) {
	    	
	        throw new ExcepcionEdicionOtroUser("No se pueden editar datos de un usuario que no es tuyo.");
	        
	    }

	    // -- Pistas de esta reserva --
	    
	    List<Long> pistasReserva = rEditar.getAsignaciones().stream()
	    					.map(a -> a.getPista().getNumero())
	    					.toList();

	    // -- Reservas ocupadas de esas pistas excluyendo la actual --
	    
	    List<Map<String, String>> reservasOcupadas = reservaService.buscarTodos().stream()
	    							.filter(r -> !r.getCodigo().equals(codigo))
	    							.flatMap(r -> r.getAsignaciones().stream()
	    									.filter(a -> pistasReserva.contains(a.getPista().getNumero()))
	    									.map(a -> Map.of(
	    										"fecha", r.getFecha().toString(),
	    										"entrada", r.getHoraEntrada().toString(),
	    										"salida", r.getHoraSalida().toString()
	    									)))
	    							.toList();

	    model.addAttribute("reserva", rEditar);
	    model.addAttribute("reservasOcupadas", reservasOcupadas);

	    porcentajeOriginal = (rEditar.getCupon() != null) ? rEditar.getCupon().getDescuento() : 0.0;

	    model.addAttribute("porcentaje", porcentajeOriginal);

	    return "admin/editarReserva";
	}
	
	@PostMapping("/editarReserva/submit")
	public String procesarEdicionReserva(
	        @ModelAttribute("reserva") Reserva r,
	        @RequestParam(value = "usaLuz", defaultValue = "false") boolean usaLuz,
	        @RequestParam("cantRaquetas") @Min(0) @Max(4) int cantPalas,
	        @RequestParam(value = "porcentaje", defaultValue = "0.0") double porcentaje,
	        @AuthenticationPrincipal UserDetails uLogueado) {

	    Reserva reservaExistente = reservaService.buscarPorId(r.getCodigo()).get();

	    boolean ocupada;
	    double precioTotal;
	    double precioPista;
	    Long numero;
	    
	    double precioConDescuento;
	    
	    // -- Comprueba que no hay solapamiento con ninguna pista distinta a las de la reserva --
	    
	    for (Asignacion a : reservaExistente.getAsignaciones()) {
	    	
	    	numero = a.getPista().getNumero();
	        
	        ocupada = reservaService.tieneConflictoHorarioEdicion(
	                numero, r.getFecha(), r.getHoraEntrada(), r.getHoraSalida(), r.getCodigo());
	        
	        if (ocupada) {
	        	
	            throw new ExcepcionTiempoReserva("La pista " + numero + " ya se encuentra reservada en el horario seleccionado.");
	            
	        }
	    }

	    
	    if (r.getHoraEntrada().isBefore(LocalTime.now()) && r.getFecha().isEqual(LocalDate.now())) {
	    	
	        throw new ExcepcionTiempoReserva("No se puede reservar la pista para hoy si la hora de entrada no es posterior a la actual");
	        
	    }

	    if (!r.getHoraEntrada().isBefore(r.getHoraSalida())) {
	    	
	        throw new ExcepcionTiempoReserva("No se puede reservar la pista. La hora de salida debe ser posterior a la de entrada");
	        
	    }

	    reservaExistente.setFecha(r.getFecha());
	    reservaExistente.setHoraEntrada(r.getHoraEntrada());
	    reservaExistente.setHoraSalida(r.getHoraSalida());

	   
	    precioTotal = 0.0;
	    
	    for (Asignacion a : reservaExistente.getAsignaciones()) {
	    	
	    	a.setUsaLuz(usaLuz);
	        a.setCantPalas(cantPalas);

	        precioPista = reservaService.calcularPrecioTotal(
	                reservaExistente.getHoraEntrada(), 
	                reservaExistente.getHoraSalida(),
	                0,
	                a.getPista().getPrecioHora(), 
	                usaLuz
	        );
	        
	        a.setPrecio(precioPista);
	        precioTotal += precioPista;
	    }
	    
	    precioTotal += reservaService.calcularPrecioPalas(cantPalas);

	    if (porcentaje > 0) {
	    	  	        
	        precioTotal = precioTotal - precioTotal * porcentaje/100; 

	        for (Asignacion a : reservaExistente.getAsignaciones()) {
	        	
	        	precioConDescuento = a.getPrecio() - (a.getPrecio() * porcentaje/100);
	        	
	            a.setPrecio(Math.round(precioConDescuento * 100.0) / 100.0);
	            
	        }
	    }
	    
	    precioTotal = Math.round(precioTotal * 100.0) / 100.0;  // -- Esto me deja dos decimales --
	    reservaExistente.setPrecioTotal(precioTotal);
	    
	    reservaService.editar(reservaExistente);

	    if (usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {
	    	
	        return "redirect:/panelAdmin?tab=reservas";
	        
	    } else {
	    	
	        return "redirect:/perfil";
	        
	    }
	}
	
	
	@GetMapping("/borrarReserva/{codigo}")
	public String borrarReserva(@PathVariable("codigo") long codigo, @AuthenticationPrincipal UserDetails uLogueado) {
		
		Optional<Reserva> rBorrar = reservaService.buscarPorId(codigo);	
		
		
		if (rBorrar.isPresent() && usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.USER) 
				&& !rBorrar.get().getUsuario().getUsername().equals(uLogueado.getUsername())) {
			
			return "home";
			
		}
			
		
		if (rBorrar.isPresent()) {
			
			reservaService.borrar(rBorrar.get());
			
		} 
		
		if (uLogueado != null && usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN))  {
			
			return "redirect:/panelAdmin";
						
		} else {
			
			return "redirect:/perfil";
			
		}
		
		
				
	}
	

	// ---- Cupones ----
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/crearCuponPromo")
	public String mostrarFormularioCrearCuponPromo(Model model) {

	    model.addAttribute("cupon", new Cupon());

	    return "admin/crearCuponPromo";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/crearCuponPromo/submit")
	public String procesarCreacionCuponPromo(@RequestParam int descuento,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaExpiracion,
	        @RequestParam(required = false) Integer usoMaximo) {

		
		if (fechaExpiracion.isBefore(LocalDate.now())) {
			
		    throw new ExcepcionCrearCupon("La fecha de expiración debe ser como mínimo el día de hoy.");
		    
		}
		
		if (descuento < 0 || descuento > 50) {
			
		    throw new ExcepcionCrearCupon("El descuento debe estar entre el 1 y 50 %");
		    
		}
		
		if (usoMaximo != null && usoMaximo <= 0) {
			
		    throw new ExcepcionCrearCupon("El cupon se debe poder usar al menos una vez.");
		    
		}
		
		
	    cuponService.crearCuponPromocional(descuento, fechaExpiracion, usoMaximo);

	    return "redirect:/panelAdmin?tab=cupones";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/borrarCupon/{id}")
	public String borrarCupon(@PathVariable Long id) {

	    Optional<Cupon> c = cuponService.buscarPorId(id);

	    if (c.isPresent() &&  historialCuponesService.cuponEnReserva(c.get().getCodigo())) {
	    	
	    	throw new ExcepcionCuponAsignado("No se puede borrar un cupón que ya ha sido usado.");
	    	
	    }
	    
	    
	    if (c.isPresent()) {
	    	
	        cuponService.borrar(c.get());
	        
	    }   
	       

	    return "redirect:/panelAdmin?tab=cupones";
	    
	}
	
	
}
