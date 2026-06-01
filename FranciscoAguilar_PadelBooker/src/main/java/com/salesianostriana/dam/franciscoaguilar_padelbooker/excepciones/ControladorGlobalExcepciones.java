package com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.AsignacionService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.CuponService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ControladorGlobalExcepciones {

	private final AsignacionService asignacionService;
	private final CuponService cuponService;
	private final ReservaService reservaService;
	private final UsuarioService usuarioService;
	private final PistaService pistaService;

	@ExceptionHandler(ExcepcionTiempoReserva.class)
    public String handleTiempoReserva(ExcepcionTiempoReserva eTiempoReserva, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
		       
        model.addAttribute("errorMensaje", eTiempoReserva.getMessage());
       
	    model.addAttribute("listaPistas", pistaService.buscarTodos());	
        
        if (usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {
        	

    	    model.addAttribute("listaReservas", reservaService.buscarTodos());
        	model.addAttribute("listaUsuarios", usuarioService.buscarTodosMenosAdminYLogeado(uLogueado.getUsername()));
        	model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
    	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
    	            		.filter(c -> c.getUsuario() != null)
    	            		.toList());
    	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
    	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
    	    
        	return "admin/panelAdmin";
        
        } else {
        	
        	return "pistas";
        	
        }
    }
	
	@ExceptionHandler(ExcepcionPistaOcupada.class)
    public String handlePistaOcupada(ExcepcionPistaOcupada ePistaOcupada, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
		       
        model.addAttribute("errorMensaje", ePistaOcupada.getMessage());
        model.addAttribute("listaUsuarios", usuarioService.buscarTodosMenosAdminYLogeado(uLogueado.getUsername()));
	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    model.addAttribute("listaReservas", reservaService.buscarTodos());
	    model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
	            		.filter(c -> c.getUsuario() != null)
	            		.toList());
	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
	    
        return "admin/panelAdmin";
    }
	
	
	@ExceptionHandler(ExcepcionNombreRepetido.class)
	public String handleNombreRepetido(ExcepcionNombreRepetido eNombreRepe, Model model) {
		
		Usuario nuevoUsuario = new Usuario();
	    
	    nuevoUsuario.setRolUsuario(RolUsuario.USER); 
	    
	    model.addAttribute("usuario", nuevoUsuario);		
		model.addAttribute("errorMensaje", eNombreRepe.getMessage());
					
		return "crearUsuario";
					
	}
	
	@ExceptionHandler(ExcepcionEmailRepetido.class)
	public String handleEmailRepetido(ExcepcionEmailRepetido eEmailRepe, Model model) {
		
		Usuario nuevoUsuario = new Usuario();
	    
	    nuevoUsuario.setRolUsuario(RolUsuario.USER); 
	    
	    model.addAttribute("usuario", nuevoUsuario);		
		model.addAttribute("errorMensaje", eEmailRepe.getMessage());
					
		return "crearUsuario";
					
	}
	
	
	@ExceptionHandler(ExcepcionEdicionNombreRepetido.class)
	public String handleEdicionNombreRepetido(ExcepcionEdicionNombreRepetido eNombreRepe, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
				
		model.addAttribute("errorMensaje", eNombreRepe.getMessage());
					
		if (usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {			
			
		    model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
		    model.addAttribute("listaPistas", pistaService.buscarTodos());
		    model.addAttribute("listaReservas", reservaService.buscarTodos());
		    model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
    	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
    	            		.filter(c -> c.getUsuario() != null)
    	            		.toList());
    	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
    	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
    	    
			return "admin/panelAdmin";
			
		} else {
			
			Usuario uActual = usuarioService.buscarPorNombre(uLogueado.getUsername()).get();
	        
	        model.addAttribute("usuario", uActual);
	        model.addAttribute("listaReservas", reservaService.buscarPorUsuario(uActual));
	        model.addAttribute("cupones", cuponService.buscarCuponesPersonalesDisponibles(uActual));
						
			return "perfil";
			
		}		
					
	}
	
	@ExceptionHandler(ExcepcionEdicionEmailRepetido.class)
	public String handleEdicionEmailRepetido(ExcepcionEdicionEmailRepetido eEmailRepe, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
				
		model.addAttribute("errorMensaje", eEmailRepe.getMessage());
					
		if (usuarioService.comprobarRol(uLogueado.getUsername(), RolUsuario.ADMIN)) {			
			
		    model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
		    model.addAttribute("listaPistas", pistaService.buscarTodos());
		    model.addAttribute("listaReservas", reservaService.buscarTodos());
		    model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
    	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
    	            		.filter(c -> c.getUsuario() != null)
    	            		.toList());
    	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
    	    
			return "admin/panelAdmin";
			
		} else {
			
			Usuario uActual = usuarioService.buscarPorNombre(uLogueado.getUsername()).get();
	        
	        model.addAttribute("usuario", uActual);
	        model.addAttribute("listaReservas", reservaService.buscarPorUsuario(uActual));
	        model.addAttribute("cupones", cuponService.buscarCuponesPersonalesDisponibles(uActual));
			
			return "perfil";
			
		}		
					
	}
	
	
	@ExceptionHandler(ExcepcionEdicionOtroUser.class)
	public String handleEdicionOtroUser(ExcepcionEdicionOtroUser eOtroUser, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
				
		model.addAttribute("errorMensaje", eOtroUser.getMessage());
		
		Usuario uActual = usuarioService.buscarPorNombre(uLogueado.getUsername()).get();
        
        model.addAttribute("usuario", uActual);
        model.addAttribute("listaReservas", reservaService.buscarPorUsuario(uActual));
        model.addAttribute("cupones", cuponService.buscarCuponesPersonalesDisponibles(uActual));
        
		return "perfil";
				
	}	
	
	@ExceptionHandler(ExcepcionCupon.class)
	public String handleExcepcionCupon(ExcepcionCupon eCupon, Model model) {
				
		model.addAttribute("errorMensaje", eCupon.getMessage());
		model.addAttribute("listaPistas", pistaService.buscarTodos());
		
		return "pistas";
				
	}
	
	@ExceptionHandler(ExcepcionCrearCupon.class)
	public String handleExcepcionCrearCupon(ExcepcionCrearCupon eCupon, Model model) {
				
		model.addAttribute("errorMensaje", eCupon.getMessage());
		model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
		model.addAttribute("listaPistas", pistaService.buscarTodos());
		model.addAttribute("listaReservas", reservaService.buscarTodos());
		model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
	            		.filter(c -> c.getUsuario() != null)
	            		.toList());
	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
	    
		return "admin/panelAdmin";
				
	}
	
	
	@ExceptionHandler(ExcepcionBorrarAdmin.class)
	public String handleExcepcionBorrarAdmin(ExcepcionBorrarAdmin e, Model model) {
				
		model.addAttribute("errorMensaje", e.getMessage());
		model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
		model.addAttribute("listaPistas", pistaService.buscarTodos());
		model.addAttribute("listaReservas", reservaService.buscarTodos());
		model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
	            		.filter(c -> c.getUsuario() != null)
	            		.toList());
	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
	    
		return "admin/panelAdmin";
				
	}
	
	
	@ExceptionHandler(ExcepcionEdicionPropioAdmin.class)
	public String handleExcepcionEdicionPropioAdmin(ExcepcionEdicionPropioAdmin e, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
				
		model.addAttribute("errorMensaje", e.getMessage());
		
		Usuario uActual = usuarioService.buscarPorNombre(uLogueado.getUsername()).get();
        
        model.addAttribute("usuario", uActual);
        model.addAttribute("listaReservas", reservaService.buscarPorUsuario(uActual));
        model.addAttribute("cupones", cuponService.buscarCuponesPersonalesDisponibles(uActual));
        
		return "perfil";
				
	}
	
	
	@ExceptionHandler(ExcepcionCuponAsignado.class)
	public String handleExcepcionCuponAsignado(ExcepcionCuponAsignado e, Model model, @AuthenticationPrincipal UserDetails uLogueado) {
				
		model.addAttribute("errorMensaje", e.getMessage());
		model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
		model.addAttribute("listaPistas", pistaService.buscarTodos());
		model.addAttribute("listaReservas", reservaService.buscarTodos());
		model.addAttribute("cuponesPromocionales", cuponService.buscarPromocionalesActivos());
	    model.addAttribute("cuponesPersonales", cuponService.buscarTodos().stream()
	            		.filter(c -> c.getUsuario() != null)
	            		.toList());
	    model.addAttribute("usuariosActivos", usuarioService.contarUsuariosNormales(RolUsuario.USER));	    
	    model.addAttribute("pistaMasUsada", asignacionService.buscarPistaMasReservada());
	    
		return "admin/panelAdmin";
				
	}
	
	
}
