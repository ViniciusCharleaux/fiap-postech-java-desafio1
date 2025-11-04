package br.com.fiap.tc.restaurant.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.tc.restaurant.dto.AuthResponseDTO;
import br.com.fiap.tc.restaurant.dto.BuscaUsuariosDTO;
import br.com.fiap.tc.restaurant.dto.LoginResquestDTO;
import br.com.fiap.tc.restaurant.dto.TrocaSenhaDTO;
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

    @Transactional(readOnly = true)
    public Page<BuscaUsuariosDTO> buscarUsuariosPorNome(String nome, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepositorio.findByNomeContainingIgnoreCase(nome, pageable);
        return usuarios.map(BuscaUsuariosDTO::new);
    }


    public AuthResponseDTO fazerLogin(LoginResquestDTO dto) {
        Usuario usuario = usuarioRepositorio.findByLogin(dto.getLogin()).orElseThrow(() -> new RuntimeException("Login inválido"));
        
        if(!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(dto.getLogin());
        return new AuthResponseDTO(token);
    }

    @Transactional
    public void trocarSenha(String login, TrocaSenhaDTO dto) {
        Usuario usuario = usuarioRepositorio.findByLogin(login)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual inválida");
        }

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuarioRepositorio.save(usuario);
    }
}
