package br.com.fiap.tc.restaurant.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProprietarioUpdateDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Valid
    private EnderecoDTO endereco;

    @NotBlank(message = "Nome do restaurante é obrigatório")
    private String nomeRestaurante;

    private String cnpj;

    private String telefoneComercial;
}
