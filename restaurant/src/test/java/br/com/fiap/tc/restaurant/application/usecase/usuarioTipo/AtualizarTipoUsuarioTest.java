package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioCreateDTO;
import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;
import br.com.fiap.tc.restaurant.domain.exceptions.DuplicateResourceException;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class AtualizarTipoUsuarioTest {

    @Mock
    private TipoUsuarioRepositorio tipoUsuarioRepositorio;

    @InjectMocks
    private AtualizarTipoUsuario atualizarTipoUsuario;

    @Test
    @DisplayName("Deve atualizar tipo existente quando não houver conflito de nome")
    void deveAtualizarSemConflito() {
        Long id = 1L;
        TipoUsuario existente = new TipoUsuario();
        existente.setId(id);
        existente.setNome("OLD");

        TipoUsuarioCreateDTO dto = new TipoUsuarioCreateDTO();
        dto.setNome("NEW");

        when(tipoUsuarioRepositorio.findById(id)).thenReturn(Optional.of(existente));
        when(tipoUsuarioRepositorio.findByNome("NEW")).thenReturn(Optional.empty());

        TipoUsuario salvo = new TipoUsuario();
        salvo.setId(id);
        salvo.setNome("NEW");
        when(tipoUsuarioRepositorio.save(any(TipoUsuario.class))).thenReturn(salvo);

        TipoUsuarioResponseDTO resp = atualizarTipoUsuario.execute(id, dto);

        assertNotNull(resp);
        assertEquals(id, resp.getId());
        assertEquals("NEW", resp.getNome());
        verify(tipoUsuarioRepositorio).save(any(TipoUsuario.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando id não existe")
    void deveLancarQuandoNaoEncontrado() {
        Long id = 99L;
        TipoUsuarioCreateDTO dto = new TipoUsuarioCreateDTO();
        dto.setNome("ANY");

        when(tipoUsuarioRepositorio.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atualizarTipoUsuario.execute(id, dto));
    }

    @Test
    @DisplayName("Deve lançar DuplicateResourceException quando nome conflita com outro registro")
    void deveLancarQuandoDuplicado() {
        Long id = 1L;
        TipoUsuario existente = new TipoUsuario();
        existente.setId(id);
        existente.setNome("OLD");

        TipoUsuario outro = new TipoUsuario();
        outro.setId(2L);
        outro.setNome("NEW");

        TipoUsuarioCreateDTO dto = new TipoUsuarioCreateDTO();
        dto.setNome("NEW");

        when(tipoUsuarioRepositorio.findById(id)).thenReturn(Optional.of(existente));
        when(tipoUsuarioRepositorio.findByNome("NEW")).thenReturn(Optional.of(outro));

        assertThrows(DuplicateResourceException.class, () -> atualizarTipoUsuario.execute(id, dto));
        verify(tipoUsuarioRepositorio, never()).save(any());
    }
}
