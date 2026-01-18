package br.com.fiap.tc.restaurant.application.usecase.proprietario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.domain.repositories.ProprietarioRepositorio;

@ExtendWith(MockitoExtension.class)
class ExcluirProprietarioTest {

    @Mock
    private ProprietarioRepositorio proprietarioRepositorio;

    @InjectMocks
    private ExcluirProprietario excluirProprietario;

    @Test
    @DisplayName("Deve excluir quando existir")
    void deveExcluir() {
        when(proprietarioRepositorio.existsById(1L)).thenReturn(true);
        excluirProprietario.execute(1L);
        verify(proprietarioRepositorio).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando não existir")
    void deveLancarNaoExiste() {
        when(proprietarioRepositorio.existsById(2L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> excluirProprietario.execute(2L));
        verify(proprietarioRepositorio, never()).deleteById(anyLong());
    }
}
