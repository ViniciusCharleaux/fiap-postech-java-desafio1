package br.com.fiap.tc.restaurant.application.usecase.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.repository.RestauranteRepository;

@ExtendWith(MockitoExtension.class)
class ExcluirRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private ExcluirRestaurante excluirRestaurante;

    @Test
    @DisplayName("Deve excluir quando existir")
    void deveExcluir() {
        when(restauranteRepository.existsById(1L)).thenReturn(true);
        excluirRestaurante.execute(1L);
        verify(restauranteRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando não existir")
    void deveLancarQuandoNaoExiste() {
        when(restauranteRepository.existsById(2L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> excluirRestaurante.execute(2L));
        verify(restauranteRepository, never()).deleteById(anyLong());
    }
}
