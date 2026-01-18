package br.com.fiap.tc.restaurant.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AtribuirTipoUsuarioDTO {
    @NotNull
    private Long tipoUsuarioId;
}
