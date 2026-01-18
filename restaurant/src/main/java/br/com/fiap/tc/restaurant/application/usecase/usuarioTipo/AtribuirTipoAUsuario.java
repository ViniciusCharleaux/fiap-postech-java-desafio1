package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.tc.restaurant.application.dto.AtribuirTipoUsuarioDTO;
import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;
import br.com.fiap.tc.restaurant.domain.entities.Usuario;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;

@Service
public class AtribuirTipoAUsuario {

    private final UsuarioRepositorio usuarioRepositorio;
    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;

    public AtribuirTipoAUsuario(UsuarioRepositorio usuarioRepositorio,
                                TipoUsuarioRepositorio tipoUsuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.tipoUsuarioRepositorio = tipoUsuarioRepositorio;
    }

    @Transactional
    public void execute(Long usuarioId, AtribuirTipoUsuarioDTO dto) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));
        TipoUsuario tipo = tipoUsuarioRepositorio.findById(dto.getTipoUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado com ID: " + dto.getTipoUsuarioId()));
        usuario.setTipoUsuario(tipo);
        usuarioRepositorio.save(usuario);
    }
}
