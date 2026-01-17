package br.com.fiap.tc.restaurant.helpers;

import org.springframework.stereotype.Component;

import br.com.fiap.tc.restaurant.dto.ClienteResponseDTO;
import br.com.fiap.tc.restaurant.dto.EnderecoDTO;
import br.com.fiap.tc.restaurant.dto.RestauranteResponseDTO;
import br.com.fiap.tc.restaurant.dto.ProprietarioResponseDTO;
import br.com.fiap.tc.restaurant.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.entities.Cliente;
import br.com.fiap.tc.restaurant.entities.Endereco;
import br.com.fiap.tc.restaurant.entities.ProprietarioRestaurante;
import br.com.fiap.tc.restaurant.entities.Restaurante;
import br.com.fiap.tc.restaurant.entities.ItemCardapio;

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
    
    public RestauranteResponseDTO converteParaRestauranteResponseDTO(Restaurante restaurante) {
        RestauranteResponseDTO dto = new RestauranteResponseDTO();
        dto.setId(restaurante.getId());
        dto.setNome(restaurante.getNome());
        dto.setEndereco(toEnderecoDTO(restaurante.getEndereco()));
        dto.setTipoDeCozinha(restaurante.getTipoDeCozinha());
        dto.setHorarioDeFuncionamento(restaurante.getHorarioDeFuncionamento());
        if (restaurante.getProprietario() != null) {
            dto.setProprietarioId(restaurante.getProprietario().getId());
            dto.setProprietarioNome(restaurante.getProprietario().getNome());
        }
        return dto;
    }
    
    public ItemCardapioResponseDTO converteParaItemCardapioResponseDTO(ItemCardapio item) {
        ItemCardapioResponseDTO dto = new ItemCardapioResponseDTO();
        dto.setId(item.getId());
        dto.setNome(item.getNome());
        dto.setDescricao(item.getDescricao());
        dto.setPreco(item.getPreco());
        dto.setApenasNoRestaurante(item.getApenasNoRestaurante());
        dto.setCaminhoFoto(item.getCaminhoFoto());
        if (item.getRestaurante() != null) {
            dto.setRestauranteId(item.getRestaurante().getId());
            dto.setRestauranteNome(item.getRestaurante().getNome());
        }
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
