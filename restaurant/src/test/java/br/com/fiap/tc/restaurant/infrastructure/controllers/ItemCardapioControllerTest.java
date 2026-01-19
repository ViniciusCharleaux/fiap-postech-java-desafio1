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

import java.math.BigDecimal;
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

import br.com.fiap.tc.restaurant.application.dto.CriarItemCardapioDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioUpdateDTO;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.AtualizarItemCardapio;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.CadastrarItemCardapio;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.ExcluirItemCardapio;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.ListarItensPorRestaurante;
import br.com.fiap.tc.restaurant.application.usecase.itemCardapio.ObterItemCardapioPorId;

@WebMvcTest(ItemCardapioController.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemCardapioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private CadastrarItemCardapio cadastrarItemCardapio;
    @MockBean private ListarItensPorRestaurante listarItensPorRestaurante;
    @MockBean private ObterItemCardapioPorId obterItemCardapioPorId;
    @MockBean private AtualizarItemCardapio atualizarItemCardapio;
    @MockBean private ExcluirItemCardapio excluirItemCardapio;

    private ItemCardapioResponseDTO resp(Long id) {
        return new ItemCardapioResponseDTO(id, "Pizza", "Mussarela", new BigDecimal("50.00"), true, null, 1L, "R1");
    }

    @Test
    @DisplayName("Deve listar itens por restaurante (200)")
    void deveListarPorRestaurante() throws Exception {
        when(listarItensPorRestaurante.execute(1L)).thenReturn(List.of(resp(1L), resp(2L)));
        mockMvc.perform(get("/api/v1/itens-cardapio/restaurante/{rid}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve buscar item por id (200)")
    void deveBuscarPorId() throws Exception {
        when(obterItemCardapioPorId.execute(5L)).thenReturn(resp(5L));
        mockMvc.perform(get("/api/v1/itens-cardapio/{id}", 5L))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve criar item (201)")
    void deveCriar() throws Exception {
        CriarItemCardapioDTO dto = new CriarItemCardapioDTO();
        dto.setNome("Pizza");
        dto.setDescricao("Mussarela");
        dto.setPreco(new BigDecimal("50.00"));
        dto.setApenasNoRestaurante(true);
        dto.setCaminhoFoto(null);
        dto.setRestauranteId(1L);

        when(cadastrarItemCardapio.execute(any(CriarItemCardapioDTO.class))).thenReturn(resp(10L));

        mockMvc.perform(post("/api/v1/itens-cardapio")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve atualizar item (200)")
    void deveAtualizar() throws Exception {
        ItemCardapioUpdateDTO dto = new ItemCardapioUpdateDTO();
        dto.setNome("Pizza Frango");
        dto.setDescricao("Frango com catupiry");
        dto.setPreco(new BigDecimal("60.00"));
        dto.setApenasNoRestaurante(false);
        dto.setCaminhoFoto(null);
        dto.setRestauranteId(1L);

        when(atualizarItemCardapio.execute(eq(3L), any(ItemCardapioUpdateDTO.class))).thenReturn(resp(3L));

        mockMvc.perform(put("/api/v1/itens-cardapio/{id}", 3L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve excluir item (204)")
    void deveExcluir() throws Exception {
        mockMvc.perform(delete("/api/v1/itens-cardapio/{id}", 4L))
            .andExpect(status().isNoContent());
    }
}
