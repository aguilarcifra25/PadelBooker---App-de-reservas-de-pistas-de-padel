package com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones;

public class ExcepcionEmailRepetido extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcepcionEmailRepetido(String msg) {
		
        super(msg);
        
    }
}
