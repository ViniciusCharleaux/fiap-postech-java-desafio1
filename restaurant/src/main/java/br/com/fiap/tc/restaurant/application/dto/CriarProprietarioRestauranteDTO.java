package br.com.fiap.tc.restaurant.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarProprietarioRestauranteDTO extends CriarUsuarioDTO {
    private String nomeRestaurante;
    private String cnpj;
    private String telefoneComercial;
}
