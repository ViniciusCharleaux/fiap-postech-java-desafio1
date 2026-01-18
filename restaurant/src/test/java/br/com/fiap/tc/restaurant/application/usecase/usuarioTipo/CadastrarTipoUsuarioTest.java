package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class CadastrarTipoUsuarioTest {

    @Mock
    private TipoUsuarioRepositorio tipoUsuarioRepositorio;

    @InjectMocks
    private CadastrarTipoUsuario cadastrarTipoUsuario;

    @Test
    @DisplayName("Deve cadastrar tipo de usuário quando nome não existe")
    void deveCadastrarQuandoNaoExiste() {
        TipoUsuarioCreateDTO dto = new TipoUsuarioCreateDTO();
        dto.setNome("ADMIN");

        when(tipoUsuarioRepositorio.existsByNome("ADMIN")).thenReturn(false);
        TipoUsuario salvo = new TipoUsuario();
        salvo.setId(1L);
        salvo.setNome("ADMIN");
        when(tipoUsuarioRepositorio.save(any(TipoUsuario.class))).thenReturn(salvo);

        TipoUsuarioResponseDTO resp = cadastrarTipoUsuario.execute(dto);

        assertNotNull(resp);
        assertEquals(1L, resp.getId());
        assertEquals("ADMIN", resp.getNome());
        verify(tipoUsuarioRepositorio).save(any(TipoUsuario.class));
    }

    @Test
    @DisplayName("Deve lançar DuplicateResourceException quando nome já existe")
    void deveLancarQuandoDuplicado() {
        TipoUsuarioCreateDTO dto = new TipoUsuarioCreateDTO();
        dto.setNome("ADMIN");

        when(tipoUsuarioRepositorio.existsByNome("ADMIN")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> cadastrarTipoUsuario.execute(dto));
        verify(tipoUsuarioRepositorio, never()).save(any());
    }
}
