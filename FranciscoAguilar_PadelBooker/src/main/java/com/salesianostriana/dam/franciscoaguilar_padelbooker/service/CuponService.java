package com.salesianostriana.dam.franciscoaguilar_padelbooker.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.excepciones.ExcepcionCupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Cupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.CuponRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.base.ServiciosBaseImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuponService extends ServiciosBaseImpl<Cupon, Long, CuponRepository>{

	private final CuponRepository cuponRepository;
	
	public String generarCodigo() {
		
		String codigo;
        boolean existe;

        
        do {
           
        	codigo = "P4D3L - " + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
                       
        	existe = cuponRepository.existsByCodigo(codigo);
            
            
        } while (existe); 

       
        return codigo;
        
    }	
	
	public String generarCodigoPromo() {
		
		String codigo;
        boolean existe;

        
        do {
           
        	codigo = "PR0M0 - " + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
                       
        	existe = cuponRepository.existsByCodigo(codigo);
            
            
        } while (existe); 

       
        return codigo;
        
    }

    // -- Los codigos que te dan si juegas mucho son del 15%, un uso y duran 3 meses desde ahora mismo --
    public void generarCuponFidelizacion(Usuario usuario) {
    	
        Cupon cupon = new Cupon();
        
        cupon.setCodigo(generarCodigo());
        cupon.setDescuento(15);
        cupon.setUsado(false);
        cupon.setFechaExpiracion(LocalDate.now().plusMonths(3));
        cupon.setUsoMaximo(1);
        cupon.setUsoActual(0);
        cupon.setUsuario(usuario);
        
        cuponRepository.save(cupon);
        
    }
    
    public void comprobarYGenerarCuponFidelizacion(Usuario usuario, List<Reserva> reservas) {
    	
        double horasTotales = reservas.stream()
            .mapToDouble(r -> Duration.between(r.getHoraEntrada(), r.getHoraSalida()).toMinutes() / 60.0)
            .sum();

        int cuponesGanados = (int) (horasTotales / 15);        
        int cuponesExistentes = cuponRepository.findByUsuario(usuario).size();
        
        if (cuponesGanados > cuponesExistentes) {
        	
            generarCuponFidelizacion(usuario);
            
        }
    }
    
    
    public void crearCuponPromocional(int descuento, LocalDate fechaExpiracion, Integer usoMaximo) {
    	
        Cupon cupon = new Cupon();
        
        cupon.setCodigo(generarCodigoPromo());
        cupon.setDescuento(descuento);
        cupon.setUsado(false);
        cupon.setFechaExpiracion(fechaExpiracion);
        cupon.setUsoMaximo(usoMaximo);
        cupon.setUsoActual(0);
        cupon.setUsuario(null);
        
        cuponRepository.save(cupon);
        
    }
	
        
    
    public Cupon validarCupon(String codigo, Usuario usuarioActual) {

        Cupon cupon = cuponRepository.findByCodigo(codigo).orElseThrow(() -> new ExcepcionCupon("Cupón no encontrado"));

        if (cupon.getFechaExpiracion().isBefore(LocalDate.now())) {
        	
            throw new ExcepcionCupon("El cupón ha expirado");
            
        }

        if (cupon.getUsuario() != null) {
           
            if (!cupon.getUsuario().getId().equals(usuarioActual.getId())) {
            	
                throw new ExcepcionCupon("Este cupón no te pertenece");
                
            }
            
            if (cupon.isUsado()) {
            	
                throw new ExcepcionCupon("Este cupón ya ha sido usado");
                
            }
            
        } else {
        	
            if (cupon.getUsoMaximo() != null && cupon.getUsoActual() >= cupon.getUsoMaximo()) {
            	
                throw new ExcepcionCupon("Este cupón ha alcanzado su límite de usos");
                
            }
        }

        return cupon;
    }
    
    
    
    
    public double aplicarDescuento(double precio, Cupon cupon) {
    	
        return precio * (1 - cupon.getDescuento() / 100.0);
        
    }
    
    public void gastarCupon(Cupon cupon) {
    	
        if (cupon.getUsuario() != null) {
        	
            cupon.setUsado(true);
            
        } else {
        	
            cupon.setUsoActual(cupon.getUsoActual() + 1);
            
        }
        
        cuponRepository.save(cupon);
    }
    
    
    
    
    
    public List<Cupon> buscarPorUsuario(Usuario usuario) {
    	
        return cuponRepository.findByUsuario(usuario);
        
    }

    public List<Cupon> buscarCuponesPersonalesDisponibles(Usuario usuario) {
    	
        return cuponRepository.findByUsuarioAndUsadoFalse(usuario);
        
    }

    public List<Cupon> buscarPromocionalesActivos() {
    	
        return cuponRepository.findByUsuarioIsNull().stream()
        							.filter(c -> c.getFechaExpiracion().isAfter(LocalDate.now()))
        							.filter(c -> c.getUsoMaximo() == null || c.getUsoActual() < c.getUsoMaximo())
        							.toList();
    }
    
    
}
