package com.salesianostriana.dam.franciscoaguilar_padelbooker.controller;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.CuponService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.ReservaService;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final CuponService cuponService;
    private final ReservaService reservaService;

    @GetMapping("/perfil")
    public String paginaMiPerfil(Model model, @AuthenticationPrincipal UserDetails usuario) {

        Optional<Usuario> u = usuarioService.buscarPorNombre(usuario.getUsername());

        model.addAttribute("usuario", u.get());
        model.addAttribute("listaReservas", reservaService.buscarPorUsuario(u.get()));
        model.addAttribute("cupones", cuponService.buscarCuponesPersonalesDisponibles(u.get()));

        return "perfil";
    }
}
	