package br.com.fiap.tc.restaurant.application.usecase.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.tc.restaurant.application.dto.TrocaSenhaDTO;
import br.com.fiap.tc.restaurant.domain.entities.Usuario;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;

@Service
public class TrocarSenhaUsuario {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public TrocarSenhaUsuario(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void execute(String login, TrocaSenhaDTO dto) {
        Usuario usuario = usuarioRepositorio.findByLogin(login)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual inválida");
        }

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuarioRepositorio.save(usuario);
    }
}
