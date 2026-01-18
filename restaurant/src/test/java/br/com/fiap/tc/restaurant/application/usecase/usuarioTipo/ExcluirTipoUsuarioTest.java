package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class ExcluirTipoUsuarioTest {

    @Mock
    private TipoUsuarioRepositorio tipoUsuarioRepositorio;

    @InjectMocks
    private ExcluirTipoUsuario excluirTipoUsuario;

    @Test
    @DisplayName("Deve excluir quando id existe")
    void deveExcluirQuandoExiste() {
        Long id = 1L;
        when(tipoUsuarioRepositorio.existsById(id)).thenReturn(true);

        excluirTipoUsuario.execute(id);

        verify(tipoUsuarioRepositorio).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando id não existe")
    void deveLancarQuandoNaoExiste() {
        Long id = 99L;
        when(tipoUsuarioRepositorio.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> excluirTipoUsuario.execute(id));
        verify(tipoUsuarioRepositorio, never()).deleteById(any());
    }
}
