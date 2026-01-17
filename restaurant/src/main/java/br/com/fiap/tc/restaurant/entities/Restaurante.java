package br.com.fiap.tc.restaurant.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurantes")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;
    @Embedded
    private Endereco endereco;
    
    @NotBlank(message = "Tipo de cozinha é obrigatório")
    @Column(nullable = false)
    private String tipoDeCozinha;
    
    @Column(nullable = false)
    private String horarioDeFuncionamento;
    
    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private ProprietarioRestaurante proprietario;
}
