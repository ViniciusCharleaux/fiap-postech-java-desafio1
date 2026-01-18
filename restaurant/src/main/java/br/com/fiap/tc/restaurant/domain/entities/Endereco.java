package br.com.fiap.tc.restaurant.domain.entities;

import br.com.fiap.tc.restaurant.application.dto.EnderecoDTO;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco {

    public Endereco(EnderecoDTO dto) {
        this.rua = dto.getRua();
        this.numero = dto.getNumero();
        this.complemento = dto.getComplemento();
        this.cidade = dto.getCidade();
        this.estado = dto.getEstado();
        this.cep = dto.getCep();
    }

    @NotBlank(message = "Rua é obrigatória")
    private String rua;
    
    @NotBlank(message = "Número é obrigatório")
    private String numero;
    
    private String complemento;
    
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;
    
    @NotBlank(message = "Estado é obrigatório")
    private String estado;
    
    @NotBlank(message = "CEP é obrigatório")
    private String cep;
}
