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

import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.entities.Endereco;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.infrastructure.repository.RestauranteRepository;

@ExtendWith(MockitoExtension.class)
class AtualizarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ProprietarioRepositorio proprietarioRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @InjectMocks
    private AtualizarRestaurante atualizarRestaurante;

    private RestauranteUpdateDTO buildDto() {
        RestauranteUpdateDTO dto = new RestauranteUpdateDTO();
        dto.setNome("R2");
        EnderecoDTO end = new EnderecoDTO();
        end.setRua("Rua F");
        end.setNumero("600");
        end.setCidade("São Paulo");
        end.setEstado("SP");
        end.setCep("06000-000");
        dto.setEndereco(end);
        dto.setTipoDeCozinha("Japonesa");
        dto.setHorarioDeFuncionamento("11-23");
        dto.setProprietarioId(8L);
        return dto;
    }

    @Test
    @DisplayName("Deve atualizar restaurante quando existir e proprietário válido")
    void deveAtualizar() {
        Long id = 3L;
        Restaurante rest = new Restaurante();
        rest.setId(id);
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(rest));

        ProprietarioRestaurante prop = new ProprietarioRestaurante();
        prop.setId(8L);
        when(proprietarioRepositorio.findById(8L)).thenReturn(Optional.of(prop));

        Restaurante salvo = new Restaurante();
        salvo.setId(id);
        salvo.setEndereco(new Endereco(buildDto().getEndereco()));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(salvo);

        RestauranteResponseDTO resp = new RestauranteResponseDTO();
        resp.setId(id);
        when(converteDTO.converteParaRestauranteResponseDTO(salvo)).thenReturn(resp);

        RestauranteResponseDTO out = atualizarRestaurante.execute(id, buildDto());
        assertEquals(id, out.getId());
        verify(restauranteRepository).save(any(Restaurante.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando restaurante não existe")
    void deveLancarRestauranteNaoExiste() {
        when(restauranteRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> atualizarRestaurante.execute(9L, buildDto()));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando proprietário não existe")
    void deveLancarProprietarioNaoExiste() {
        Long id = 3L;
        when(restauranteRepository.findById(id)).thenReturn(Optional.of(new Restaurante()));
        when(proprietarioRepositorio.findById(8L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> atualizarRestaurante.execute(id, buildDto()));
    }
}
