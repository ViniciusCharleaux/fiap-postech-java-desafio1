package br.com.fiap.tc.restaurant.application.usecase.cliente;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.domain.repositories.ClienteRepositorio;

@ExtendWith(MockitoExtension.class)
class ExcluirClienteTest {

    @Mock
    private ClienteRepositorio clienteRepositorio;

    @InjectMocks
    private ExcluirCliente excluirCliente;

    @Test
    @DisplayName("Deve excluir quando existir")
    void deveExcluir() {
        when(clienteRepositorio.existsById(1L)).thenReturn(true);
        excluirCliente.execute(1L);
        verify(clienteRepositorio).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando não existir")
    void deveLancarQuandoNaoExiste() {
        when(clienteRepositorio.existsById(2L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> excluirCliente.execute(2L));
        verify(clienteRepositorio, never()).deleteById(anyLong());
    }
}
