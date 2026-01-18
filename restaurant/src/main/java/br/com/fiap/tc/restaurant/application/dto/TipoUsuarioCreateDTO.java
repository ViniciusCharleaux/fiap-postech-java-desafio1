package br.com.fiap.tc.restaurant.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoUsuarioCreateDTO {
    @NotBlank
    private String nome;
}
