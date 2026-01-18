package br.com.fiap.tc.restaurant.application.usecase.itemCardapio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.repository.ItemCardapioRepository;

@ExtendWith(MockitoExtension.class)
class ExcluirItemCardapioTest {

    @Mock
    private ItemCardapioRepository itemCardapioRepository;

    @InjectMocks
    private ExcluirItemCardapio excluirItemCardapio;

    @Test
    @DisplayName("Deve excluir quando existir")
    void deveExcluir() {
        when(itemCardapioRepository.existsById(1L)).thenReturn(true);
        excluirItemCardapio.execute(1L);
        verify(itemCardapioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando não existir")
    void deveLancarQuandoNaoExiste() {
        when(itemCardapioRepository.existsById(2L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> excluirItemCardapio.execute(2L));
        verify(itemCardapioRepository, never()).deleteById(anyLong());
    }
}
