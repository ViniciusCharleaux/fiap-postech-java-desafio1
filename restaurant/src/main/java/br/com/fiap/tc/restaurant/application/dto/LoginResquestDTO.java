package br.com.fiap.tc.restaurant.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResquestDTO {
    private String login;
    private String senha;
}
