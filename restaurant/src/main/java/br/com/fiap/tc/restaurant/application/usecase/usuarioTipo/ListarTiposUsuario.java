package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@Service
public class ListarTiposUsuario {

    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;

    public ListarTiposUsuario(TipoUsuarioRepositorio tipoUsuarioRepositorio) {
        this.tipoUsuarioRepositorio = tipoUsuarioRepositorio;
    }

    public List<TipoUsuarioResponseDTO> execute() {
        return tipoUsuarioRepositorio.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TipoUsuarioResponseDTO toDto(TipoUsuario t) {
        TipoUsuarioResponseDTO dto = new TipoUsuarioResponseDTO();
        dto.setId(t.getId());
        dto.setNome(t.getNome());
        return dto;
    }
}
