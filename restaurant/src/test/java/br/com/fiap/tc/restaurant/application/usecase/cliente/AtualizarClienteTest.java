package br.com.fiap.tc.restaurant.application.usecase.cliente;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.application.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ClienteUpdateDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.domain.entities.Cliente;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.ClienteRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;

@ExtendWith(MockitoExtension.class)
class AtualizarClienteTest {

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @InjectMocks
    private AtualizarCliente atualizarCliente;

    private ClienteUpdateDTO buildDto() {
        ClienteUpdateDTO dto = new ClienteUpdateDTO();
        dto.setNome("Novo Nome");
        dto.setEmail("novo@exemplo.com");
        EnderecoDTO end = new EnderecoDTO();
        end.setRua("Rua B");
        end.setNumero("200");
        end.setCidade("São Paulo");
        end.setEstado("SP");
        end.setCep("02000-000");
        dto.setEndereco(end);
        dto.setTelefone("1188888888");
        return dto;
    }

    @Test
    @DisplayName("Deve atualizar cliente existente")
    void deveAtualizar() {
        Long id = 1L;
        Cliente existente = new Cliente();
        existente.setId(id);
        existente.setEmail("old@exemplo.com");

        when(clienteRepositorio.findById(id)).thenReturn(Optional.of(existente));
        when(usuarioRepositorio.findByEmail("novo@exemplo.com")).thenReturn(Optional.empty());

        Cliente salvo = new Cliente();
        salvo.setId(id);
        salvo.setNome("Novo Nome");
        salvo.setEmail("novo@exemplo.com");
        salvo.setEndereco(new Endereco(buildDto().getEndereco()));
        when(clienteRepositorio.save(any(Cliente.class))).thenReturn(salvo);

        ClienteResponseDTO resp = new ClienteResponseDTO();
        resp.setId(id);
        resp.setNome("Novo Nome");
        when(converteDTO.converteParaClienteResponseDTO(salvo)).thenReturn(resp);

        ClienteResponseDTO out = atualizarCliente.execute(id, buildDto());

        assertNotNull(out);
        assertEquals(id, out.getId());
        assertEquals("Novo Nome", out.getNome());
        verify(clienteRepositorio).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando cliente não existe")
    void deveLancarNaoEncontrado() {
        when(clienteRepositorio.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> atualizarCliente.execute(99L, buildDto()));
    }
}
