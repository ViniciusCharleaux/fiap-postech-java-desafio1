package br.com.fiap.tc.restaurant.application.usecase.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.AuthResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.LoginResquestDTO;
import br.com.fiap.tc.restaurant.domain.entities.Usuario;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class FazerLogin {

    private static final String SECRET_KEY = "MINHA_CHAVE_SUPER_SECRETA_DE_PELO_MENOS_32_CARACTERES";

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public FazerLogin(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO execute(LoginResquestDTO dto) {
        Usuario usuario = usuarioRepositorio.findByLogin(dto.getLogin())
            .orElseThrow(() -> new RuntimeException("Login inválido"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(dto.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new AuthResponseDTO(token);
    }
}
