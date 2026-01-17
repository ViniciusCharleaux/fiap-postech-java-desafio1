package br.com.fiap.tc.restaurant.restaurante.application.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.ItemCardapioRepository;

@Service
public class ObterItemCardapioPorId {

    private final ItemCardapioRepository itemCardapioRepository;
    private final ConverteDTO converteDTO;

    public ObterItemCardapioPorId(ItemCardapioRepository itemCardapioRepository, ConverteDTO converteDTO) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.converteDTO = converteDTO;
    }

    public ItemCardapioResponseDTO execute(Long id) {
        ItemCardapio item = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item de cardápio não encontrado com ID: " + id));
        return converteDTO.converteParaItemCardapioResponseDTO(item);
    }
}
