package br.com.fiap.tc.restaurant.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuscaClientesRequestDTO {

    private String nome;
    private String offset;
    private String size;

}
