package br.com.fiap.tc.restaurant.application.usecase.itemCardapio;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.CriarItemCardapioDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.infrastructure.repository.ItemCardapioRepository;
import br.com.fiap.tc.restaurant.domain.repositories.RestauranteRepositorio;

@Service
public class CadastrarItemCardapio {

    private final ItemCardapioRepository itemCardapioRepository;
    private final RestauranteRepositorio restauranteRepositorio;
    private final ConverteDTO converteDTO;

    public CadastrarItemCardapio(ItemCardapioRepository itemCardapioRepository,
                                 RestauranteRepositorio restauranteRepositorio,
                                 ConverteDTO converteDTO) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.restauranteRepositorio = restauranteRepositorio;
        this.converteDTO = converteDTO;
    }

    public ItemCardapioResponseDTO execute(CriarItemCardapioDTO dto) {
        Restaurante restaurante = restauranteRepositorio.findById(dto.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + dto.getRestauranteId()));

        ItemCardapio item = new ItemCardapio();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setApenasNoRestaurante(dto.getApenasNoRestaurante());
        item.setCaminhoFoto(dto.getCaminhoFoto());
        item.setRestaurante(restaurante);

        ItemCardapio salvo = itemCardapioRepository.save(item);
        return converteDTO.converteParaItemCardapioResponseDTO(salvo);
    }
}
