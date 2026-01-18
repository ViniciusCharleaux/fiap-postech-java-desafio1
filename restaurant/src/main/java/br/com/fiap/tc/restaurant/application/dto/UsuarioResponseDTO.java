package br.com.fiap.tc.restaurant.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String login;
    private EnderecoDTO endereco;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;
    private String userType;
}
