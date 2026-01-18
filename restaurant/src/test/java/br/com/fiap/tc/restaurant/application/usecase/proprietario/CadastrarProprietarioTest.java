package br.com.fiap.tc.restaurant.application.usecase.proprietario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.tc.restaurant.application.dto.CriarProprietarioRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;

@ExtendWith(MockitoExtension.class)
class CadastrarProprietarioTest {

    @Mock
    private ProprietarioRepositorio proprietarioRepositorio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CadastrarProprietario cadastrarProprietario;

    private CriarProprietarioRestauranteDTO buildDto() {
        CriarProprietarioRestauranteDTO dto = new CriarProprietarioRestauranteDTO();
        dto.setNome("Maria");
        dto.setEmail("maria@exemplo.com");
        dto.setLogin("maria");
        dto.setSenha("abc");
        dto.setNomeRestaurante("Rest Maria");
        dto.setCnpj("11222333000100");
        dto.setTelefoneComercial("1133334444");
        EnderecoDTO end = new EnderecoDTO();
        end.setRua("Rua C");
        end.setNumero("300");
        end.setCidade("São Paulo");
        end.setEstado("SP");
        end.setCep("03000-000");
        dto.setEndereco(end);
        return dto;
    }

    @Test
    @DisplayName("Deve cadastrar proprietario quando dados válidos e não duplicados")
    void deveCadastrar() {
        CriarProprietarioRestauranteDTO dto = buildDto();
        when(usuarioRepositorio.existsByEmail(dto.getEmail())).thenReturn(false);
        when(usuarioRepositorio.existsByLogin(dto.getLogin())).thenReturn(false);
        when(proprietarioRepositorio.existsByCnpj(dto.getCnpj())).thenReturn(false);
        when(passwordEncoder.encode("abc")).thenReturn("ENC-ABC");

        ProprietarioRestaurante salvo = new ProprietarioRestaurante();
        salvo.setId(10L);
        salvo.setNome(dto.getNome());
        salvo.setEmail(dto.getEmail());
        salvo.setLogin(dto.getLogin());
        salvo.setSenha("ENC-ABC");
        salvo.setEndereco(new Endereco(dto.getEndereco()));
        salvo.setCnpj(dto.getCnpj());
        salvo.setNomeRestaurante(dto.getNomeRestaurante());

        when(proprietarioRepositorio.save(any(ProprietarioRestaurante.class))).thenReturn(salvo);

        ProprietarioResponseDTO resp = new ProprietarioResponseDTO();
        resp.setId(10L);
        when(converteDTO.convertePaProprietarioResponseDTO(salvo)).thenReturn(resp);

        ProprietarioResponseDTO out = cadastrarProprietario.execute(dto);
        assertEquals(10L, out.getId());
        verify(proprietarioRepositorio).save(any(ProprietarioRestaurante.class));
    }

    @Test
    @DisplayName("Deve lançar DuplicateResourceException quando e-mail/login/cnpj duplicado")
    void deveLancarDuplicado() {
        CriarProprietarioRestauranteDTO dto = buildDto();
        when(usuarioRepositorio.existsByEmail(dto.getEmail())).thenReturn(true);
        assertThrows(DuplicateResourceException.class, () -> cadastrarProprietario.execute(dto));
    }
}
