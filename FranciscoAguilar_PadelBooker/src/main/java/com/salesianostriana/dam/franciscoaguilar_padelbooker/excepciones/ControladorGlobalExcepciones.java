package com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.PistaService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControladorGlobalExcepciones {

	private final PistaService pistaService;

	ControladorGlobalExcepciones(PistaService pistaService) {
		this.pistaService = pistaService;
	}

	@ExceptionHandler(ExcepcionTiempoReserva.class)
    public String handleSinPlazas(ExcepcionTiempoReserva eTiempoReserva, Model model) {
		       
        model.addAttribute("errorMensaje", eTiempoReserva.getMessage());
        model.addAttribute("listaPistas", pistaService.buscarTodos());	
        
        return "pistas";
    }
	
	
}
