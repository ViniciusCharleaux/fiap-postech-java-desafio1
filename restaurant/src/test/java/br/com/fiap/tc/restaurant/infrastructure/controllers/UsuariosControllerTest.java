package br.com.fiap.tc.restaurant.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fiap.tc.restaurant.application.dto.AtribuirTipoUsuarioDTO;
import br.com.fiap.tc.restaurant.application.dto.BuscaUsuariosDTO;
import br.com.fiap.tc.restaurant.application.usecase.auth.BuscarUsuariosPorNome;
import br.com.fiap.tc.restaurant.application.usecase.auth.TrocarSenhaUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.AtribuirTipoAUsuario;

import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = UsuariosController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuariosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuscarUsuariosPorNome buscarUsuariosPorNome;

    @MockBean
    private TrocarSenhaUsuario trocarSenhaUsuario;

    @MockBean
    private AtribuirTipoAUsuario atribuirTipoAUsuario;

    @Test
    @DisplayName("GET /api/v1/usuarios deve retornar 200 e invocar use case")
    void getUsuarios_ok() throws Exception {
        given(buscarUsuariosPorNome.execute(eq(""), any())).willReturn(Page.empty());

        mockMvc.perform(get("/api/v1/usuarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(buscarUsuariosPorNome).execute(eq(""), any());
    }

    @Test
    @DisplayName("PATCH /api/v1/usuarios/{id}/tipo-usuario deve retornar 204 e invocar use case")
    void patchAtribuirTipoUsuario_noContent() throws Exception {
        String body = "{\"tipoUsuarioId\":1}";

        mockMvc.perform(patch("/api/v1/usuarios/{usuarioId}/tipo-usuario", 10L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNoContent());

        verify(atribuirTipoAUsuario).execute(eq(10L), any(AtribuirTipoUsuarioDTO.class));
    }
}
