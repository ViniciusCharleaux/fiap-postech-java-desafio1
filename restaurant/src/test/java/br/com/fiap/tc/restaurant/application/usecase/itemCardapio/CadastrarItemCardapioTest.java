package br.com.fiap.tc.restaurant.application.usecase.itemCardapio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.application.dto.CriarItemCardapioDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.infrastructure.repository.ItemCardapioRepository;
import br.com.fiap.tc.restaurant.domain.repositories.RestauranteRepositorio;

@ExtendWith(MockitoExtension.class)
class CadastrarItemCardapioTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Mock
    private RestauranteRepositorio restauranteRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @InjectMocks
    private CadastrarItemCardapio cadastrarItemCardapio;

    private CriarItemCardapioDTO buildDto() {
        CriarItemCardapioDTO dto = new CriarItemCardapioDTO();
        dto.setNome("Pizza");
        dto.setDescricao("Deliciosa");
        dto.setPreco(new BigDecimal("39.90"));
        dto.setApenasNoRestaurante(true);
        dto.setCaminhoFoto("/img.png");
        dto.setRestauranteId(4L);
        return dto;
    }

    @Test
    @DisplayName("Deve cadastrar item quando restaurante existe")
    void deveCadastrar() {
        when(restauranteRepositorio.findById(4L)).thenReturn(Optional.of(new Restaurante()));
        ItemCardapio salvo = new ItemCardapio();
        salvo.setId(100L);
        when(itemCardapioRepository.save(any(ItemCardapio.class))).thenReturn(salvo);

        ItemCardapioResponseDTO resp = new ItemCardapioResponseDTO();
        resp.setId(100L);
        when(converteDTO.converteParaItemCardapioResponseDTO(salvo)).thenReturn(resp);

        ItemCardapioResponseDTO out = cadastrarItemCardapio.execute(buildDto());
        assertEquals(100L, out.getId());
        verify(itemCardapioRepository).save(any(ItemCardapio.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando restaurante não existe")
    void deveLancarSemRestaurante() {
        when(restauranteRepositorio.findById(4L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> cadastrarItemCardapio.execute(buildDto()));
    }
}
