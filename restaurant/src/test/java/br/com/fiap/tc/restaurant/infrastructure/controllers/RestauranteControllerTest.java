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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.tc.restaurant.application.dto.CriarRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.RestauranteUpdateDTO;
import br.com.fiap.tc.restaurant.application.usecase.restaurante.AtualizarRestaurante;
import br.com.fiap.tc.restaurant.application.usecase.restaurante.CadastrarRestaurante;
import br.com.fiap.tc.restaurant.application.usecase.restaurante.ExcluirRestaurante;
import br.com.fiap.tc.restaurant.application.usecase.restaurante.ListarRestaurantes;
import br.com.fiap.tc.restaurant.application.usecase.restaurante.ObterRestaurantePorId;

@WebMvcTest(RestauranteController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CadastrarRestaurante cadastrarRestaurante;

    @MockBean
    private ListarRestaurantes listarRestaurantes;

    @MockBean
    private ObterRestaurantePorId obterRestaurantePorId;

    @MockBean
    private AtualizarRestaurante atualizarRestaurante;

    @MockBean
    private ExcluirRestaurante excluirRestaurante;

    private RestauranteResponseDTO sampleResponse(Long id) {
        RestauranteResponseDTO dto = new RestauranteResponseDTO();
        dto.setId(id);
        dto.setNome("R1");
        dto.setTipoDeCozinha("Italiana");
        dto.setHorarioDeFuncionamento("10:00-22:00");
        dto.setProprietarioId(10L);
        dto.setProprietarioNome("João");
        return dto;
    }

    @Test
    @DisplayName("Deve listar restaurantes (200)")
    void deveListar() throws Exception {
        when(listarRestaurantes.execute()).thenReturn(List.of(sampleResponse(1L), sampleResponse(2L)));

        mockMvc.perform(get("/api/v1/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve buscar por id (200)")
    void deveBuscarPorId() throws Exception {
        when(obterRestaurantePorId.execute(1L)).thenReturn(sampleResponse(1L));

        mockMvc.perform(get("/api/v1/restaurantes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve criar restaurante (201)")
    void deveCriar() throws Exception {
        CriarRestauranteDTO dto = new CriarRestauranteDTO();
        dto.setNome("R1");
        EnderecoDTO end = new EnderecoDTO("Rua A", "100", null, "São Paulo", "SP", "01000-000");
        dto.setEndereco(end);
        dto.setTipoDeCozinha("Italiana");
        dto.setHorarioDeFuncionamento("10:00-22:00");
        dto.setProprietarioId(10L);

        RestauranteResponseDTO resp = sampleResponse(99L);
        when(cadastrarRestaurante.execute(any(CriarRestauranteDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/v1/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve atualizar restaurante (200)")
    void deveAtualizar() throws Exception {
        RestauranteUpdateDTO dto = new RestauranteUpdateDTO();
        dto.setNome("R1-upd");
        EnderecoDTO end = new EnderecoDTO("Rua B", "200", null, "São Paulo", "SP", "02000-000");
        dto.setEndereco(end);
        dto.setTipoDeCozinha("Japonesa");
        dto.setHorarioDeFuncionamento("11:00-23:00");
        dto.setProprietarioId(11L);

        RestauranteResponseDTO resp = sampleResponse(1L);
        when(atualizarRestaurante.execute(eq(1L), any(RestauranteUpdateDTO.class))).thenReturn(resp);

        mockMvc.perform(put("/api/v1/restaurantes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve excluir restaurante (204)")
    void deveExcluir() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurantes/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
