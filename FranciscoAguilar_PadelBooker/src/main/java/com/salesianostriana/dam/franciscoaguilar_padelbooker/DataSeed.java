package com.salesianostriana.dam.franciscoaguilar_padelbooker;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.AsignacionRepository;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.CuponRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Asignacion;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.AsignacionPK;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Cupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.PistaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.ReservaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.UsuarioRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeed {

	private final CuponRepository cuponRepository;
	private final AsignacionRepository asignacionRepository;
	private final PistaRepository pistaRepository;
	private final ReservaRepository reservaRepository;
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder encoder;
	
	@PostConstruct
	public void inicio() {
		
		
		// -- Pistas --
		
		Pista p1 = Pista.builder()
                .tipo("Indoor")
                .suelo("Cemento poroso")
                .precioHora(12)
                .imagen("https://res.cloudinary.com/playtomic/image/upload/c_scale,w_3840,q_80,f_auto/pro/tenants/d89b7dce-c0e0-4e52-b206-0a3491fb1845/padelindooroarso_0001")
                .build();         
    
		Pista p2 = Pista.builder()
                .tipo("Indoor")
                .suelo("Cemento poroso")
                .precioHora(12)
                .imagen("https://res.cloudinary.com/playtomic/image/upload/c_scale,w_3840,q_80,f_auto/pro/tenants/d89b7dce-c0e0-4e52-b206-0a3491fb1845/padelindooroarso_0001")
                .build();
		
		Pista p3 = Pista.builder()
                .tipo("Indoor")
                .suelo("Cemento poroso")
                .precioHora(12)
                .imagen("https://res.cloudinary.com/playtomic/image/upload/c_scale,w_3840,q_80,f_auto/pro/tenants/d89b7dce-c0e0-4e52-b206-0a3491fb1845/padelindooroarso_0001")
                .build();
		
	    Pista p4 = Pista.builder()
	                .tipo("Outdoor")
	                .suelo("Césped artificial")
	                .imagen("https://www.elbierzodigital.com/wp-content/uploads/2022/05/PADEL2-1200x900.jpg")
	                .precioHora(12)
	                .build(); 
	    
	    Pista p5 = Pista.builder()
                .tipo("Outdoor")
                .suelo("Césped artificial")
                .imagen("https://www.elbierzodigital.com/wp-content/uploads/2022/05/PADEL2-1200x900.jpg")
                .precioHora(12)
                .build();
	    
	    Pista p6 = Pista.builder()
                .tipo("Outdoor")
                .suelo("Césped artificial")
                .imagen("https://www.elbierzodigital.com/wp-content/uploads/2022/05/PADEL2-1200x900.jpg")
                .precioHora(12)
                .build();
	    
	    Pista p7 = Pista.builder()
	                .tipo("Frontón")
	                .suelo("Cemento poroso")
	                .precioHora(6)
	                .imagen("https://www.elbarraco.org/wp-content/uploads/2017/07/fronton2.jpg")
	                .build(); 
	    
	    Pista p8 = Pista.builder()
                .tipo("Mini 1vs1")
                .suelo("Cemento poroso")
                .precioHora(8)
                .imagen("https://m3padelacademy.com/wp-content/uploads/2023/02/pista-individual.jpg")
                .build(); 
	    
	    Pista p9 = Pista.builder()
                .tipo("Mini 1vs1")
                .suelo("Cemento poroso")
                .precioHora(8)
                .imagen("https://m3padelacademy.com/wp-content/uploads/2023/02/pista-individual.jpg")
                .build(); 
	    
	    
	 // -- Usuarios --
	    
	    Usuario u1 = Usuario.builder()
	                    .username("Paco")
	                    .password(encoder.encode("123"))
	                    .email("aguilar.cifra25@triana.salesianos.edu")
	                    .telefono("123456789")
	                    .rolUsuario(RolUsuario.ADMIN)
	                    .build();
	    
	    Usuario u2 = Usuario.builder()
	                .username("Francis")
	                .password(encoder.encode("123"))
	                .email("francispadel@gmail.com")
	                .telefono("321547698")
	                .rolUsuario(RolUsuario.USER)
	                .build();
	    
	    Usuario u3 = Usuario.builder()
	                .username("Curro")
	                .password(encoder.encode("123"))
	                .email("currillopillo@gmail.com")
	                .telefono("987654321")
	                .rolUsuario(RolUsuario.USER)
	                .build();
	    
	    Usuario u4 = Usuario.builder()
	    				.username("Bea")
	    				.password(encoder.encode("123"))
	    				.email("padelb3a@gmail.com")
	    				.telefono("321654987")
	    				.rolUsuario(RolUsuario.ADMIN)
	    				.build();
	    
	    Usuario user = Usuario.builder()
	                .email("user@user.com")
	                .username("user")
	                .password(encoder.encode("user"))
	                .rolUsuario(RolUsuario.USER)
	                .build();
	    
	    Usuario admin = Usuario.builder()
	                .email("admin@admin.com")
	                .username("admin")
	                .password(encoder.encode("admin"))
	                .rolUsuario(RolUsuario.ADMIN)
	                .build();
	    
	 // -- Reservas y asignaciones --
	    
	    Reserva r1 = Reserva.builder()
	                .fecha(LocalDate.now().plusDays(1))
	                .horaEntrada(LocalTime.of(9, 0, 0))
	                .horaSalida(LocalTime.of(10, 0, 0))
	                .precioTotal(12)
	                .usuario(user)  
	                .build();
	    
	    Asignacion a1 = Asignacion.builder()
	                    .asignacionPK(new AsignacionPK())                        
	                    .cantRaquetas(0)
	                    .usaLuz(false)
	                    .precio(r1.getPrecioTotal())
	                    .observaciones("")
	                    .build();
	    
	    a1.agregarEnPista(p1); 
	    a1.agregarEnReserva(r1);
	            
	    pistaRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9));
	
	    usuarioRepository.saveAll(List.of(user, admin, u1, u2, u3, u4));
	    	    
	    reservaRepository.save(r1);
	    asignacionRepository.save(a1);
	    
	    // -- Cupones --
	    
	    Cupon cuponPromo = new Cupon();
	    cuponPromo.setCodigo("PR0M0 - VERANO26");
	    cuponPromo.setDescuento(20);
	    cuponPromo.setFechaExpiracion(LocalDate.now().plusMonths(3));
	    cuponPromo.setUsoMaximo(50);
	    cuponPromo.setUsoActual(0);
	    cuponPromo.setUsuario(null);

	    Cupon cuponPromo2 = new Cupon();
	    cuponPromo2.setCodigo("PR0M0 - F1ND3CURS0");
	    cuponPromo2.setDescuento(25);
	    cuponPromo2.setFechaExpiracion(LocalDate.now().plusMonths(1));
	    cuponPromo2.setUsoMaximo(50);
	    cuponPromo2.setUsoActual(0);
	    cuponPromo2.setUsuario(null);
	    
	    Cupon cuponUser = new Cupon();
	    cuponUser.setCodigo("P4D3L - FIDELUSER");
	    cuponUser.setDescuento(15);
	    cuponUser.setFechaExpiracion(LocalDate.now().plusMonths(3));
	    cuponUser.setUsoMaximo(1);
	    cuponUser.setUsoActual(0);
	    cuponUser.setUsuario(user);

	    cuponRepository.saveAll(List.of(cuponPromo, cuponPromo2, cuponUser));
	    
	    
		
	}
	
}
