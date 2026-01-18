package br.com.fiap.tc.restaurant.application.usecase.proprietario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.application.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioUpdateDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;

@ExtendWith(MockitoExtension.class)
class AtualizarProprietarioTest {

    @Mock
    private ProprietarioRepositorio proprietarioRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @InjectMocks
    private AtualizarProprietario atualizarProprietario;

    private ProprietarioUpdateDTO buildDto() {
        ProprietarioUpdateDTO dto = new ProprietarioUpdateDTO();
        dto.setNome("Novo Prop");
        dto.setEmail("novo@prop.com");
        dto.setCnpj("00998877000155");
        dto.setNomeRestaurante("Novo R");
        dto.setTelefoneComercial("1144445555");
        EnderecoDTO end = new EnderecoDTO();
        end.setRua("Rua D");
        end.setNumero("400");
        end.setCidade("São Paulo");
        end.setEstado("SP");
        end.setCep("04000-000");
        dto.setEndereco(end);
        return dto;
    }

    @Test
    @DisplayName("Deve atualizar proprietario existente")
    void deveAtualizar() {
        Long id = 5L;
        ProprietarioRestaurante existente = new ProprietarioRestaurante();
        existente.setId(id);

        when(proprietarioRepositorio.findById(id)).thenReturn(Optional.of(existente));
        ProprietarioRestaurante salvo = new ProprietarioRestaurante();
        salvo.setId(id);
        salvo.setEndereco(new Endereco(buildDto().getEndereco()));
        when(proprietarioRepositorio.save(any(ProprietarioRestaurante.class))).thenReturn(salvo);

        ProprietarioResponseDTO resp = new ProprietarioResponseDTO();
        resp.setId(id);
        when(converteDTO.convertePaProprietarioResponseDTO(salvo)).thenReturn(resp);

        ProprietarioResponseDTO out = atualizarProprietario.execute(id, buildDto());
        assertEquals(id, out.getId());
        verify(proprietarioRepositorio).save(any(ProprietarioRestaurante.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando não existe")
    void deveLancarNaoEncontrado() {
        when(proprietarioRepositorio.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> atualizarProprietario.execute(99L, buildDto()));
    }
}
