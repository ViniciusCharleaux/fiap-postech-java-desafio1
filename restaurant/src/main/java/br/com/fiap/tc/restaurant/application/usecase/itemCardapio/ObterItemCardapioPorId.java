package br.com.fiap.tc.restaurant.application.usecase.itemCardapio;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.application.dto.ItemCardapioResponseDTO;
import br.com.fiap.tc.restaurant.domain.entities.ItemCardapio;
import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.infrastructure.helpers.ConverteDTO;
import br.com.fiap.tc.restaurant.infrastructure.repository.ItemCardapioRepository;

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
