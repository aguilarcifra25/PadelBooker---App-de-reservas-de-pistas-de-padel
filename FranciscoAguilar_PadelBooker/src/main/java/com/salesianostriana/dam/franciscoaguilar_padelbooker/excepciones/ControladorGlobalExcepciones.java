package com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControladorGlobalExcepciones {

	private final ReservaService reservaService;
	private final UsuarioService usuarioService;
	private final PistaService pistaService;

	ControladorGlobalExcepciones(PistaService pistaService, UsuarioService usuarioService, ReservaService reservaService) {
		this.pistaService = pistaService;
		this.usuarioService = usuarioService;
		this.reservaService = reservaService;
	}

	@ExceptionHandler(ExcepcionTiempoReserva.class)
    public String handleTiempoReserva(ExcepcionTiempoReserva eTiempoReserva, Model model) {
		       
        model.addAttribute("errorMensaje", eTiempoReserva.getMessage());
        model.addAttribute("listaPistas", pistaService.buscarTodos());	
        
        return "pistas";
    }
	
	@ExceptionHandler(ExcepcionPistaOcupada.class)
    public String handlePistaOcupada(ExcepcionPistaOcupada ePistaOcupada, Model model) {
		       
        model.addAttribute("errorMensaje", ePistaOcupada.getMessage());
        model.addAttribute("listaUsuarios", usuarioService.buscarTodos());
	    model.addAttribute("listaPistas", pistaService.buscarTodos());
	    model.addAttribute("listaReservas", reservaService.buscarTodos());
        
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
	
}
