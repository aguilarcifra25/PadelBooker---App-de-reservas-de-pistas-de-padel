package com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base;

import java.util.List;
import java.util.Optional;

public interface ServiciosBase<T, ID> {

	List<T> buscarTodos();
	
	Optional<T> buscarPorId (ID id);
	
	T guardar (T t);
	
	T editar (T t);
	
	void borrar (T t);
	
	void borrarPorId (ID id);
	
	
}
