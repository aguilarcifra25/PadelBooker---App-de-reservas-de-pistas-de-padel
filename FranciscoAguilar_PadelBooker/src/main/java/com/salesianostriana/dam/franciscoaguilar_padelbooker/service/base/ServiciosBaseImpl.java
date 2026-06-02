package com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class ServiciosBaseImpl<T, ID, R extends JpaRepository<T, ID>> implements ServiciosBase<T, ID> {

	@Autowired
	protected R repository;

	@Override
	public List<T> buscarTodos() {
		return repository.findAll();
	}

	@Override
	public Optional<T> buscarPorId (ID id) {
		return repository.findById(id);
	}

	@Override
	public T guardar (T t) {
		return repository.save(t);
	}

	@Override
	public T editar (T t) {
		return repository.save(t);
	}

	@Override
	public void borrar (T t) {
		repository.delete(t);

	}

	@Override
	public void borrarPorId (ID id) {
		repository.deleteById(id);
	}
	
	
}
