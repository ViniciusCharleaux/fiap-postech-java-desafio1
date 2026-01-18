package br.com.fiap.tc.restaurant.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.CriarItemCardapioDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.application.dto.ItemCardapioUpdateDTO;
import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.domain.entities.Restaurante;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.domain.repositories.ItemCardapioRepositorio;
import br.com.fiap.tc.restaurant.domain.repositories.RestauranteRepositorio;

@Service
public class ItemCardapioService {

    private final ItemCardapioRepositorio itemCardapioRepositorio;
    private final RestauranteRepositorio restauranteRepositorio;
    private final ConverteDTO converteDTO;

    public ItemCardapioService(ItemCardapioRepositorio itemCardapioRepositorio,
                               RestauranteRepositorio restauranteRepositorio,
                               ConverteDTO converteDTO) {
        this.itemCardapioRepositorio = itemCardapioRepositorio;
        this.restauranteRepositorio = restauranteRepositorio;
        this.converteDTO = converteDTO;
    }

    public List<ItemCardapioResponseDTO> listarPorRestaurante(Long restauranteId) {
        return itemCardapioRepositorio.findByRestauranteId(restauranteId)
                .stream()
                .map(converteDTO::converteParaItemCardapioResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemCardapioResponseDTO buscarPorId(Long id) {
        ItemCardapio item = itemCardapioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio não encontrado com ID: " + id));
        return converteDTO.converteParaItemCardapioResponseDTO(item);
    }

    public ItemCardapioResponseDTO criar(CriarItemCardapioDTO dto) {
        Restaurante restaurante = restauranteRepositorio.findById(dto.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + dto.getRestauranteId()));

        ItemCardapio item = new ItemCardapio();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setApenasNoRestaurante(dto.getApenasNoRestaurante());
        item.setCaminhoFoto(dto.getCaminhoFoto());
        item.setRestaurante(restaurante);

        ItemCardapio salvo = itemCardapioRepositorio.save(item);
        return converteDTO.converteParaItemCardapioResponseDTO(salvo);
    }

    public ItemCardapioResponseDTO atualizar(Long id, ItemCardapioUpdateDTO dto) {
        ItemCardapio item = itemCardapioRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio não encontrado com ID: " + id));

        Restaurante restaurante = restauranteRepositorio.findById(dto.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + dto.getRestauranteId()));

        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setPreco(dto.getPreco());
        item.setApenasNoRestaurante(dto.getApenasNoRestaurante());
        item.setCaminhoFoto(dto.getCaminhoFoto());
        item.setRestaurante(restaurante);

        ItemCardapio atualizado = itemCardapioRepositorio.save(item);
        return converteDTO.converteParaItemCardapioResponseDTO(atualizado);
    }

    public void excluir(Long id) {
        if (!itemCardapioRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("Item de cardápio não encontrado com ID: " + id);
        }
        itemCardapioRepositorio.deleteById(id);
    }
}
