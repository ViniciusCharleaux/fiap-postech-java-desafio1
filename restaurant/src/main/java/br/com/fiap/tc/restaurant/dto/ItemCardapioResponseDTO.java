package br.com.fiap.tc.restaurant.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCardapioResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean apenasNoRestaurante;
    private String caminhoFoto;
    private Long restauranteId;
    private String restauranteNome;
}
