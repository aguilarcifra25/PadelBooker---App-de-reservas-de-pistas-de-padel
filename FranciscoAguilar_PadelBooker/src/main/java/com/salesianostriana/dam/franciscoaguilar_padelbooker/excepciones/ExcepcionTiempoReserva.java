package com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExcepcionTiempoReserva extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcepcionTiempoReserva(String msg) {
		
        super(msg);
        
    }
		
}
