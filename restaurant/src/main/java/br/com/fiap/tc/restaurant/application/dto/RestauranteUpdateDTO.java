package br.com.fiap.tc.restaurant.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteUpdateDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Valid
    @NotNull(message = "Endereço é obrigatório")
    private EnderecoDTO endereco;

    @NotBlank(message = "Tipo de cozinha é obrigatório")
    private String tipoDeCozinha;

    @NotBlank(message = "Horário de funcionamento é obrigatório")
    private String horarioDeFuncionamento;

    @NotNull(message = "Proprietário é obrigatório")
    private Long proprietarioId;
}
