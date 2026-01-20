package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.application.dto.AtribuirTipoUsuarioDTO;
import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;
import br.com.fiap.tc.restaurant.domain.entities.Usuario;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.UsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class AtribuirTipoAUsuarioTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private TipoUsuarioRepositorio tipoUsuarioRepositorio;

    @InjectMocks
    private AtribuirTipoAUsuario usecase;

    private AtribuirTipoUsuarioDTO dto(long tipoId){
        AtribuirTipoUsuarioDTO d = new AtribuirTipoUsuarioDTO();
        d.setTipoUsuarioId(tipoId);
        return d;
    }

    @Test
    @DisplayName("Deve atribuir tipo ao usuário quando ambos existem")
    void deveAtribuir() {
        Long usuarioId = 5L;
        when(usuarioRepositorio.findById(usuarioId)).thenReturn(Optional.of(new Usuario()));
        when(tipoUsuarioRepositorio.findById(2L)).thenReturn(Optional.of(new TipoUsuario()));

        assertDoesNotThrow(() -> usecase.execute(usuarioId, dto(2L)));
        verify(usuarioRepositorio).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando usuário não existe")
    void deveLancarUsuarioNaoExiste() {
        when(usuarioRepositorio.findById(7L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> usecase.execute(7L, dto(1L)));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando tipo de usuário não existe")
    void deveLancarTipoNaoExiste() {
        Long usuarioId = 8L;
        when(usuarioRepositorio.findById(usuarioId)).thenReturn(Optional.of(new Usuario()));
        when(tipoUsuarioRepositorio.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> usecase.execute(usuarioId, dto(99L)));
    }
}
