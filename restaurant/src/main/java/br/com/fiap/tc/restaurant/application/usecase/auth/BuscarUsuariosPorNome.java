package br.com.fiap.tc.restaurant.application.usecase.auth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.BuscaUsuariosDTO;
import br.com.fiap.tc.restaurant.domain.entities.Usuario;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;

@Service
public class BuscarUsuariosPorNome {

    private final UsuarioRepositorio usuarioRepositorio;

    public BuscarUsuariosPorNome(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Page<BuscaUsuariosDTO> execute(String nome, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepositorio.findByNomeContainingIgnoreCase(nome, pageable);
        return usuarios.map(BuscaUsuariosDTO::new);
    }
}
