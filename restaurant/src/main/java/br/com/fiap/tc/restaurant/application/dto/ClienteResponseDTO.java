package br.com.fiap.tc.restaurant.application.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO extends UsuarioResponseDTO {
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
}
