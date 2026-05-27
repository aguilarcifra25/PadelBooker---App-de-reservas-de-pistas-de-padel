package com.salesianostriana.dam.franciscoaguilar_padelbooker;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.AsignacionRepository;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Asignacion;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.AsignacionPK;
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

	private final AsignacionRepository asignacionRepository;
	private final PistaRepository pistaRepository;
	private final ReservaRepository reservaRepository;
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder encoder;
	
	@PostConstruct
	public void inicio() {
		
		Pista p1 = Pista.builder()
                .tipo("Indoor")
                .suelo("Césped artificial")
                .precioHora(12)
                .imagen("https://res.cloudinary.com/playtomic/image/upload/c_scale,w_3840,q_80,f_auto/pro/tenants/d89b7dce-c0e0-4e52-b206-0a3491fb1845/padelindooroarso_0001")
                .build();         
    
    Pista p2 = Pista.builder()
                .tipo("Outdoor")
                .suelo("Cemento poroso")
                .imagen("https://www.elbierzodigital.com/wp-content/uploads/2022/05/PADEL2-1200x900.jpg")
                .precioHora(12)
                .build(); 
    
    Pista p3 = Pista.builder()
                .tipo("Frontón")
                .suelo("Cemento poroso")
                .precioHora(6)
                .imagen("https://www.elbarraco.org/wp-content/uploads/2017/07/fronton2.jpg")
                .build(); 
    
    Usuario u1 = Usuario.builder()
                    .username("Paco")
                    .password(encoder.encode("123"))
                    .email("aguilar.cifra25@triana.salesianos.edu")
                    .telefono("123456789")
                    .rolUsuario(RolUsuario.USER)
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
    
    Reserva r1 = Reserva.builder()
                .fecha(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
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
            
    pistaRepository.save(p1);
    pistaRepository.save(p2);
    pistaRepository.save(p3);

    usuarioRepository.save(user);
    usuarioRepository.save(admin);
    usuarioRepository.save(u1);
    usuarioRepository.save(u2);
    usuarioRepository.save(u3);
    
    reservaRepository.save(r1);
    asignacionRepository.save(a1);
		
	}
	
}
