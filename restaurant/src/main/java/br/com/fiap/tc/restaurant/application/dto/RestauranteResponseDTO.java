package br.com.fiap.tc.restaurant.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteResponseDTO {
    private Long id;
    private String nome;
    private EnderecoDTO endereco;
    private String tipoDeCozinha;
    private String horarioDeFuncionamento;
    private Long proprietarioId;
    private String proprietarioNome;
}
