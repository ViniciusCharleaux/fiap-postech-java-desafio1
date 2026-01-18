package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioCreateDTO;
import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;
import br.com.fiap.tc.restaurant.domain.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@Service
public class CadastrarTipoUsuario {

    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;

    public CadastrarTipoUsuario(TipoUsuarioRepositorio tipoUsuarioRepositorio) {
        this.tipoUsuarioRepositorio = tipoUsuarioRepositorio;
    }

    public TipoUsuarioResponseDTO execute(TipoUsuarioCreateDTO dto) {
        if (tipoUsuarioRepositorio.existsByNome(dto.getNome())) {
            throw new DuplicateResourceException("Tipo de usuário já existe: " + dto.getNome());
        }
        TipoUsuario tipo = new TipoUsuario();
        tipo.setNome(dto.getNome());
        TipoUsuario salvo = tipoUsuarioRepositorio.save(tipo);
        TipoUsuarioResponseDTO resp = new TipoUsuarioResponseDTO();
        resp.setId(salvo.getId());
        resp.setNome(salvo.getNome());
        return resp;
    }
}
