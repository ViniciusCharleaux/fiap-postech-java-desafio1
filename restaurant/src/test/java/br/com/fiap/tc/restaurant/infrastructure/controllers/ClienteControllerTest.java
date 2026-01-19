package br.com.fiap.tc.restaurant.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
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

import br.com.fiap.tc.restaurant.application.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ClienteUpdateDTO;
import br.com.fiap.tc.restaurant.application.dto.CriarClienteDTO;
import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.application.usecase.cliente.AtualizarCliente;
import br.com.fiap.tc.restaurant.application.usecase.cliente.CadastrarCliente;
import br.com.fiap.tc.restaurant.application.usecase.cliente.ExcluirCliente;

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CadastrarCliente cadastrarCliente;

    @MockBean
    private ExcluirCliente excluirCliente;

    @MockBean
    private AtualizarCliente atualizarCliente;

    private CriarClienteDTO sampleCriar() {
        CriarClienteDTO dto = new CriarClienteDTO();
        dto.setNome("João");
        dto.setEmail("joao@ex.com");
        dto.setLogin("joao");
        dto.setSenha("123");
        dto.setCpf("12345678900");
        dto.setTelefone("11999999999");
        EnderecoDTO end = new EnderecoDTO("Rua A", "100", null, "São Paulo", "SP", "01000-000");
        dto.setEndereco(end);
        return dto;
    }

    private ClienteResponseDTO sampleResp(Long id) {
        ClienteResponseDTO r = new ClienteResponseDTO();
        r.setId(id);
        r.setNome("João");
        return r;
    }

    @Test
    @DisplayName("Deve cadastrar cliente (201)")
    void deveCadastrar() throws Exception {
        CriarClienteDTO payload = sampleCriar();
        when(cadastrarCliente.execute(any(CriarClienteDTO.class))).thenReturn(sampleResp(1L));

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve atualizar cliente (200)")
    void deveAtualizar() throws Exception {
        ClienteUpdateDTO payload = new ClienteUpdateDTO();
        payload.setNome("João Silva");
        payload.setEmail("joaos@ex.com");
        payload.setTelefone("11888888888");
        payload.setDataNascimento(null);
        payload.setEndereco(new EnderecoDTO("Rua B", "200", null, "São Paulo", "SP", "02000-000"));

        when(atualizarCliente.execute(eq(1L), any(ClienteUpdateDTO.class))).thenReturn(sampleResp(1L));

        mockMvc.perform(put("/api/v1/clientes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve excluir cliente (204)")
    void deveExcluir() throws Exception {
        mockMvc.perform(delete("/api/v1/clientes/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
