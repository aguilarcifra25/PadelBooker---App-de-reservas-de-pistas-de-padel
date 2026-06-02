package com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones;

public class ExcepcionNombreRepetido extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcepcionNombreRepetido(String msg) {
		
        super(msg);
        
    }
	
}
