package br.com.fiap.tc.restaurant.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "proprietarios_restaurante")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder()
public class ProprietarioRestaurante extends Usuario {
    
    @Column(name = "nome_restaurante")
    private String nomeRestaurante;
    
    @Column(name = "cnpj", unique = true)
    private String cnpj;
    
    @Column(name = "telefone_comercial")
    private String telefoneComercial;
}
