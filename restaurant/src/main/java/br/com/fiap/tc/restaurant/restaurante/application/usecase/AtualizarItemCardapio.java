package br.com.fiap.tc.restaurant.restaurante.application.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.dto.ItemCardapioUpdateDTO;
import br.com.fiap.tc.restaurant.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.entities.Restaurante;
import br.com.fiap.tc.restaurant.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.repositories.RestauranteRepositorio;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.ItemCardapioRepository;

@Service
public class AtualizarItemCardapio {

    private final ItemCardapioRepository itemCardapioRepository;
    private final RestauranteRepositorio restauranteRepositorio;
    private final ConverteDTO converteDTO;

    public AtualizarItemCardapio(ItemCardapioRepository itemCardapioRepository,
                                 RestauranteRepositorio restauranteRepositorio,
                                 ConverteDTO converteDTO) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.restauranteRepositorio = restauranteRepositorio;
        this.converteDTO = converteDTO;
    }

    public ItemCardapioResponseDTO execute(Long id, ItemCardapioUpdateDTO dto) {
        ItemCardapio item = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio não encontrado com ID: " + id));

        Restaurante restaurante = restauranteRepositorio.findById(dto.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + dto.getRestauranteId()));

        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setApenasNoRestaurante(dto.getApenasNoRestaurante());
        item.setCaminhoFoto(dto.getCaminhoFoto());
        item.setRestaurante(restaurante);

        ItemCardapio atualizado = itemCardapioRepository.save(item);
        return converteDTO.converteParaItemCardapioResponseDTO(atualizado);
    }
}
