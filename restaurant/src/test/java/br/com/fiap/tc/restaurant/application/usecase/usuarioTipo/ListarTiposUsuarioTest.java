package br.com.fiap.tc.restaurant.application.usecase.usuarioTipo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.TipoUsuario;
import br.com.fiap.tc.restaurant.domain.repositories.TipoUsuarioRepositorio;

@ExtendWith(MockitoExtension.class)
class ListarTiposUsuarioTest {

    @Mock
    private TipoUsuarioRepositorio tipoUsuarioRepositorio;

    @InjectMocks
    private ListarTiposUsuario listarTiposUsuario;

    @Test
    @DisplayName("Deve listar tipos de usuário convertendo para DTO")
    void deveListar() {
        TipoUsuario t1 = new TipoUsuario();
        t1.setId(1L);
        t1.setNome("ADMIN");
        TipoUsuario t2 = new TipoUsuario();
        t2.setId(2L);
        t2.setNome("CLIENTE");

        when(tipoUsuarioRepositorio.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<TipoUsuarioResponseDTO> resp = listarTiposUsuario.execute();

        assertEquals(2, resp.size());
        assertEquals(1L, resp.get(0).getId());
        assertEquals("ADMIN", resp.get(0).getNome());
        assertEquals(2L, resp.get(1).getId());
        assertEquals("CLIENTE", resp.get(1).getNome());
    }
}
