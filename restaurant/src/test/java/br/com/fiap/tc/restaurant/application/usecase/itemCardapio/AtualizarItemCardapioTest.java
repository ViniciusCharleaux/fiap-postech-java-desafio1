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

import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.infrastructure.repository.ItemCardapioRepository;
import br.com.fiap.tc.restaurant.domain.repositories.RestauranteRepositorio;

@ExtendWith(MockitoExtension.class)
class AtualizarItemCardapioTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @Mock
    private RestauranteRepositorio restauranteRepositorio;

    @Mock
    private ConverteDTO converteDTO;

    @InjectMocks
    private AtualizarItemCardapio atualizarItemCardapio;

    private ItemCardapioUpdateDTO buildDto() {
        ItemCardapioUpdateDTO dto = new ItemCardapioUpdateDTO();
        dto.setNome("Pizza GG");
        dto.setDescricao("Muito grande");
        dto.setPreco(new BigDecimal("59.90"));
        dto.setApenasNoRestaurante(false);
        dto.setCaminhoFoto("/img2.png");
        dto.setRestauranteId(4L);
        return dto;
    }

    @Test
    @DisplayName("Deve atualizar item quando id existe e restaurante válido")
    void deveAtualizar() {
        Long id = 11L;
        when(itemCardapioRepository.findById(id)).thenReturn(Optional.of(new ItemCardapio()));
        when(restauranteRepositorio.findById(4L)).thenReturn(Optional.of(new Restaurante()));

        ItemCardapio salvo = new ItemCardapio();
        salvo.setId(id);
        when(itemCardapioRepository.save(any(ItemCardapio.class))).thenReturn(salvo);

        ItemCardapioResponseDTO resp = new ItemCardapioResponseDTO();
        resp.setId(id);
        when(converteDTO.converteParaItemCardapioResponseDTO(salvo)).thenReturn(resp);

        ItemCardapioResponseDTO out = atualizarItemCardapio.execute(id, buildDto());
        assertEquals(id, out.getId());
        verify(itemCardapioRepository).save(any(ItemCardapio.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando item não existe")
    void deveLancarItemNaoExiste() {
        when(itemCardapioRepository.findById(11L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> atualizarItemCardapio.execute(11L, buildDto()));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando restaurante não existe")
    void deveLancarRestauranteNaoExiste() {
        when(itemCardapioRepository.findById(11L)).thenReturn(Optional.of(new ItemCardapio()));
        when(restauranteRepositorio.findById(4L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> atualizarItemCardapio.execute(11L, buildDto()));
    }
}
