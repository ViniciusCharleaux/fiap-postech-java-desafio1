package br.com.fiap.tc.restaurant.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.application.dto.CriarRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.infrastructure.repository.RestauranteRepository;

@ExtendWith(MockitoExtension.class)
class CadastrarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ProprietarioRepositorio proprietarioRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @InjectMocks
    private CadastrarRestaurante cadastrarRestaurante;

    private CriarRestauranteDTO buildDto() {
        CriarRestauranteDTO dto = new CriarRestauranteDTO();
        dto.setNome("R1");
        EnderecoDTO end = new EnderecoDTO();
        end.setRua("Rua E");
        end.setNumero("500");
        end.setCidade("São Paulo");
        end.setEstado("SP");
        end.setCep("05000-000");
        dto.setEndereco(end);
        dto.setTipoDeCozinha("Italiana");
        dto.setHorarioDeFuncionamento("10-22");
        dto.setProprietarioId(7L);
        return dto;
    }

    @Test
    @DisplayName("Deve cadastrar restaurante quando proprietário existe")
    void deveCadastrar() {
        CriarRestauranteDTO dto = buildDto();
        ProprietarioRestaurante prop = new ProprietarioRestaurante();
        prop.setId(7L);
        when(proprietarioRepositorio.findById(7L)).thenReturn(Optional.of(prop));

        Restaurante salvo = new Restaurante();
        salvo.setId(20L);
        salvo.setNome(dto.getNome());
        salvo.setEndereco(new Endereco(dto.getEndereco()));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(salvo);

        RestauranteResponseDTO resp = new RestauranteResponseDTO();
        resp.setId(20L);
        when(converteDTO.converteParaRestauranteResponseDTO(salvo)).thenReturn(resp);

        RestauranteResponseDTO out = cadastrarRestaurante.execute(dto);
        assertEquals(20L, out.getId());
        verify(restauranteRepository).save(any(Restaurante.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando proprietário não existe")
    void deveLancarQuandoSemProprietario() {
        when(proprietarioRepositorio.findById(7L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> cadastrarRestaurante.execute(buildDto()));
    }
}
