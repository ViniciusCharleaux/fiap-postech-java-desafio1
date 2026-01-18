package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioCreateDTO;
import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;
import br.com.fiap.tc.restaurant.domain.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@Service
public class AtualizarTipoUsuario {

    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;

    public AtualizarTipoUsuario(TipoUsuarioRepositorio tipoUsuarioRepositorio) {
        this.tipoUsuarioRepositorio = tipoUsuarioRepositorio;
    }

    public TipoUsuarioResponseDTO execute(Long id, TipoUsuarioCreateDTO dto) {
        TipoUsuario existente = tipoUsuarioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado com ID: " + id));

        tipoUsuarioRepositorio.findByNome(dto.getNome()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new DuplicateResourceException("Tipo de usuário já existe: " + dto.getNome());
            }
        });

        existente.setNome(dto.getNome());
        TipoUsuario salvo = tipoUsuarioRepositorio.save(existente);
        TipoUsuarioResponseDTO resp = new TipoUsuarioResponseDTO();
        resp.setId(salvo.getId());
        resp.setNome(salvo.getNome());
        return resp;
    }
}
