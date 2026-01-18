package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@Service
public class ExcluirTipoUsuario {

    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;

    public ExcluirTipoUsuario(TipoUsuarioRepositorio tipoUsuarioRepositorio) {
        this.tipoUsuarioRepositorio = tipoUsuarioRepositorio;
    }

    public void execute(Long id) {
        if (!tipoUsuarioRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de usuário não encontrado com ID: " + id);
        }
        tipoUsuarioRepositorio.deleteById(id);
    }
}
