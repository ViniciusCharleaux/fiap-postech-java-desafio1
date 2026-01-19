package br.com.fiap.tc.restaurant.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import br.com.fiap.tc.restaurant.application.dto.AuthResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.LoginResquestDTO;
import br.com.fiap.tc.restaurant.application.usecase.auth.FazerLogin;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private FazerLogin fazerLogin;

    @Test
    @DisplayName("Deve efetuar login (201)")
    void deveLogar() throws Exception {
        LoginResquestDTO dto = new LoginResquestDTO();
        dto.setLogin("user");
        dto.setSenha("pass");

        AuthResponseDTO resp = new AuthResponseDTO();
        resp.setToken("jwt.token");
        when(fazerLogin.execute(any(LoginResquestDTO.class))).thenReturn(resp);

        mockMvc.perform(post("/api/v1/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
