package br.com.fiap.tc.restaurant.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.ItemCardapioRepository;

@Service
public class ListarItensPorRestaurante {

    private final ItemCardapioRepository itemCardapioRepository;
    private final ConverteDTO converteDTO;

    public ListarItensPorRestaurante(ItemCardapioRepository itemCardapioRepository, ConverteDTO converteDTO) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.converteDTO = converteDTO;
    }

    public List<ItemCardapioResponseDTO> execute(Long restauranteId) {
        return itemCardapioRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(converteDTO::converteParaItemCardapioResponseDTO)
                .collect(Collectors.toList());
    }
}
