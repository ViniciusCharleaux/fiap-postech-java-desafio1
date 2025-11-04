package br.com.fiap.tc.restaurant.dto;

import br.com.fiap.tc.restaurant.entities.Endereco;
import br.com.fiap.tc.restaurant.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuscaUsuariosDTO {
    
    private Long id;
    private String nome;
    private String email;
    private String login;
    private Endereco endereco;

    public BuscaUsuariosDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.login = usuario.getLogin();
        this.endereco = usuario.getEndereco();
    }
}
