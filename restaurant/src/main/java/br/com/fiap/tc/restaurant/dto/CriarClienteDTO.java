package br.com.fiap.tc.restaurant.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarClienteDTO extends CriarUsuarioDTO{
    private String cpf;
    private String telefone;
    private LocalDate dataNascimento;
}
