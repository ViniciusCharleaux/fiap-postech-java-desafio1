package br.com.fiap.tc.restaurant.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioCreateDTO;
import br.com.fiap.tc.restaurant.application.dto.TipoUsuarioResponseDTO;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.AtualizarTipoUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.CadastrarTipoUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.ExcluirTipoUsuario;
import br.com.fiap.tc.restaurant.application.usecase.usuarioTipo.ListarTiposUsuario;

@WebMvcTest(TipoUsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class TipoUsuarioControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private CadastrarTipoUsuario cadastrarTipoUsuario;
    @MockBean private ListarTiposUsuario listarTiposUsuario;
    @MockBean private AtualizarTipoUsuario atualizarTipoUsuario;
    @MockBean private ExcluirTipoUsuario excluirTipoUsuario;

    private TipoUsuarioResponseDTO resp(Long id, String nome) {
        TipoUsuarioResponseDTO r = new TipoUsuarioResponseDTO();
        r.setId(id);
        r.setNome(nome);
        return r;
    }

    @Test
    @DisplayName("Deve listar tipos de usuário (200)")
    void deveListar() throws Exception {
        when(listarTiposUsuario.execute()).thenReturn(List.of(resp(1L, "ADMIN"), resp(2L, "CLIENTE")));
        mockMvc.perform(get("/api/v1/tipos-usuario"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve criar tipo de usuário (201)")
    void deveCriar() throws Exception {
        TipoUsuarioCreateDTO dto = new TipoUsuarioCreateDTO();
        dto.setNome("GERENTE");
        when(cadastrarTipoUsuario.execute(any(TipoUsuarioCreateDTO.class))).thenReturn(resp(5L, "GERENTE"));
        mockMvc.perform(post("/api/v1/tipos-usuario")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve atualizar tipo de usuário (200)")
    void deveAtualizar() throws Exception {
        TipoUsuarioCreateDTO dto = new TipoUsuarioCreateDTO();
        dto.setNome("SUPERVISOR");
        when(atualizarTipoUsuario.execute(eq(3L), any(TipoUsuarioCreateDTO.class))).thenReturn(resp(3L, "SUPERVISOR"));
        mockMvc.perform(put("/api/v1/tipos-usuario/{id}", 3L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve excluir tipo de usuário (204)")
    void deveExcluir() throws Exception {
        mockMvc.perform(delete("/api/v1/tipos-usuario/{id}", 6L))
            .andExpect(status().isNoContent());
    }
}
