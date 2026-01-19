package br.com.fiap.tc.restaurant.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.tc.restaurant.application.dto.CriarProprietarioRestauranteDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ProprietarioUpdateDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.application.usecase.proprietario.AtualizarProprietario;
import br.com.fiap.tc.restaurant.application.usecase.proprietario.CadastrarProprietario;
import br.com.fiap.tc.restaurant.application.usecase.proprietario.ExcluirProprietario;

@WebMvcTest(ProprietarioRestauranteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProprietarioRestauranteControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private CadastrarProprietario cadastrarProprietario;
    @MockBean private ExcluirProprietario excluirProprietario;
    @MockBean private AtualizarProprietario atualizarProprietario;

    private CriarProprietarioRestauranteDTO novo() {
        CriarProprietarioRestauranteDTO d = new CriarProprietarioRestauranteDTO();
        d.setNome("Maria");
        d.setEmail("maria@ex.com");
        d.setLogin("maria");
        d.setSenha("123");
        d.setTelefoneComercial("1133333333");
        d.setCnpj("12345678000100");
        d.setNomeRestaurante("R1");
        d.setEndereco(new EnderecoDTO("Rua C", "10", null, "SP", "SP", "01010-010"));
        return d;
    }

    private ProprietarioResponseDTO resp(Long id) {
        ProprietarioResponseDTO r = new ProprietarioResponseDTO();
        r.setId(id);
        r.setNome("Maria");
        return r;
    }

    @Test
    @DisplayName("Deve cadastrar proprietário (201)")
    void deveCadastrar() throws Exception {
        when(cadastrarProprietario.execute(any(CriarProprietarioRestauranteDTO.class))).thenReturn(resp(1L));
        mockMvc.perform(post("/api/v1/proprietarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(novo())))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve atualizar proprietário (200)")
    void deveAtualizar() throws Exception {
        ProprietarioUpdateDTO up = new ProprietarioUpdateDTO();
        up.setNome("Maria S");
        up.setEmail("marias@ex.com");
        up.setTelefoneComercial("1144444444");
        up.setCnpj("12345678000100");
        up.setNomeRestaurante("R1");
        up.setEndereco(new EnderecoDTO("Rua D", "20", null, "SP", "SP", "02020-020"));
        when(atualizarProprietario.execute(eq(2L), any(ProprietarioUpdateDTO.class))).thenReturn(resp(2L));
        mockMvc.perform(put("/api/v1/proprietarios/{id}", 2L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(up)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve excluir proprietário (204)")
    void deveExcluir() throws Exception {
        mockMvc.perform(delete("/api/v1/proprietarios/{id}", 3L))
            .andExpect(status().isNoContent());
    }
}
