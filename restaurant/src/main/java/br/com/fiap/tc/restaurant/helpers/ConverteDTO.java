package br.com.fiap.tc.restaurant.helpers;

import org.springframework.stereotype.Component;

import br.com.fiap.tc.restaurant.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.entities.Cliente;
import br.com.fiap.tc.restaurant.entities.Endereco;
import br.com.fiap.tc.restaurant.entities.ProprietarioRestaurante;

@Component
public class ConverteDTO {

    public ClienteResponseDTO converteParaClienteResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setLogin(cliente.getLogin());
        dto.setEndereco(toEnderecoDTO(cliente.getEndereco()));
        dto.setDataCriacao(cliente.getDataCriacao());
        dto.setDataUltimaAlteracao(cliente.getDataUltimaAlteracao());
        dto.setUserType("CLIENTE");
        dto.setCpf(cliente.getCpf());
        dto.setTelefone(cliente.getTelefone());
        dto.setDataNascimento(cliente.getDataNascimento());
        return dto;
    }

    public ProprietarioResponseDTO convertePaProprietarioResponseDTO(ProprietarioRestaurante proprietario) {
        ProprietarioResponseDTO dto = new ProprietarioResponseDTO();
        dto.setId(proprietario.getId());
        dto.setNomeRestaurante(proprietario.getNomeRestaurante());
        dto.setCnpj(proprietario.getCnpj());
        dto.setTelefoneComercial(proprietario.getTelefoneComercial());
        dto.setEndereco(toEnderecoDTO(proprietario.getEndereco()));
        dto.setDataCriacao(proprietario.getDataCriacao());
        dto.setDataUltimaAlteracao(proprietario.getDataUltimaAlteracao());
        dto.setUserType("PROPRIETARIO");
        return dto;
    }
    
    
    private EnderecoDTO toEnderecoDTO(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoDTO(
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
    }
}
