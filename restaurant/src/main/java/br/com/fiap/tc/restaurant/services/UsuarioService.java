package br.com.fiap.tc.restaurant.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.dto.AuthResponseDTO;
import br.com.fiap.tc.restaurant.dto.LoginResquestDTO;
import br.com.fiap.tc.restaurant.entities.Usuario;
import br.com.fiap.tc.restaurant.repositories.UsuarioRepositorio;

@Service
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public UsuarioService(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    
    public AuthResponseDTO fazerLogin(LoginResquestDTO dto) {

        Usuario usuario = usuarioRepositorio.findByLogin(dto.getLogin()).orElseThrow(() -> new RuntimeException("Login inválido"));
        
        if(!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(dto.getLogin());

        return new AuthResponseDTO(token);
    }

}
