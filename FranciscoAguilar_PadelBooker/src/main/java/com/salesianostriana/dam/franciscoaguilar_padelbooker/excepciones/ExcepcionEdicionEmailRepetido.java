package com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones;

public class ExcepcionEdicionEmailRepetido extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcepcionEdicionEmailRepetido (String msg) {
		
		super(msg);
		
	}
	
}
