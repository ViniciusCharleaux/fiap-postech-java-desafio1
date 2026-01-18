package br.com.fiap.tc.restaurant.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario{
    @Column(name = "cpf", unique = true)
    private String cpf;
    
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
}
