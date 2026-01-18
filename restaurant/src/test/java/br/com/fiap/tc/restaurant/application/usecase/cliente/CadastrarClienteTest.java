package br.com.fiap.tc.restaurant.application.usecase.cliente;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.tc.restaurant.application.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.CriarClienteDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.domain.entities.Cliente;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.domain.repositories.ClienteRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;

@ExtendWith(MockitoExtension.class)
class CadastrarClienteTest {

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CadastrarCliente cadastrarCliente;

    private CriarClienteDTO buildDto() {
        CriarClienteDTO dto = new CriarClienteDTO();
        dto.setNome("João");
        dto.setEmail("joao@exemplo.com");
        dto.setLogin("joao");
        dto.setSenha("123");
        dto.setCpf("12345678900");
        dto.setTelefone("11999999999");
        EnderecoDTO end = new EnderecoDTO();
        end.setRua("Rua A");
        end.setNumero("100");
        end.setCidade("São Paulo");
        end.setEstado("SP");
        end.setCep("01000-000");
        dto.setEndereco(end);
        return dto;
    }

    @Test
    @DisplayName("Deve cadastrar cliente quando não houver e-mail ou CPF duplicado")
    void deveCadastrar() {
        CriarClienteDTO dto = buildDto();

        when(usuarioRepositorio.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clienteRepositorio.existsByCpf(dto.getCpf())).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("ENC-123");

        Cliente saved = new Cliente();
        saved.setId(1L);
        saved.setNome(dto.getNome());
        saved.setEmail(dto.getEmail());
        saved.setLogin(dto.getLogin());
        saved.setSenha("ENC-123");
        saved.setEndereco(new Endereco(dto.getEndereco()));
        saved.setCpf(dto.getCpf());

        when(clienteRepositorio.save(any(Cliente.class))).thenReturn(saved);

        ClienteResponseDTO respMock = new ClienteResponseDTO();
        respMock.setId(1L);
        respMock.setNome("João");
        when(converteDTO.converteParaClienteResponseDTO(saved)).thenReturn(respMock);

        ClienteResponseDTO resp = cadastrarCliente.execute(dto);

        assertNotNull(resp);
        assertEquals(1L, resp.getId());
        assertEquals("João", resp.getNome());
        verify(clienteRepositorio).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar DuplicateResourceException quando e-mail já existe")
    void deveLancarEmailDuplicado() {
        CriarClienteDTO dto = buildDto();
        when(usuarioRepositorio.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> cadastrarCliente.execute(dto));
        verify(clienteRepositorio, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar DuplicateResourceException quando CPF já existe")
    void deveLancarCpfDuplicado() {
        CriarClienteDTO dto = buildDto();
        when(usuarioRepositorio.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clienteRepositorio.existsByCpf(dto.getCpf())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> cadastrarCliente.execute(dto));
        verify(clienteRepositorio, never()).save(any());
    }
}
