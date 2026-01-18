package br.com.fiap.tc.restaurant.application.usecase.proprietario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.tc.restaurant.application.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;

@Service
public class AtualizarProprietario {

    private final ProprietarioRepositorio proprietarioRepositorio;
    private final ConverteDTO converteDTO;

    public AtualizarProprietario(ProprietarioRepositorio proprietarioRepositorio,
                                 ConverteDTO converteDTO) {
        this.proprietarioRepositorio = proprietarioRepositorio;
        this.converteDTO = converteDTO;
    }

    @Transactional
    public ProprietarioResponseDTO execute(Long id, ProprietarioUpdateDTO dto) {
        ProprietarioRestaurante proprietario = proprietarioRepositorio.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado com ID: " + id));

        Endereco endereco = new Endereco(dto.getEndereco());

        proprietario.setNomeRestaurante(dto.getNomeRestaurante());
        proprietario.setTelefoneComercial(dto.getTelefoneComercial());
        proprietario.setEmail(dto.getEmail());
        proprietario.setCnpj(dto.getCnpj());
        proprietario.setEndereco(endereco);
        proprietario.setNome(dto.getNome());

        ProprietarioRestaurante updatedProprietario = proprietarioRepositorio.save(proprietario);
        return converteDTO.convertePaProprietarioResponseDTO(updatedProprietario);
    }
}
