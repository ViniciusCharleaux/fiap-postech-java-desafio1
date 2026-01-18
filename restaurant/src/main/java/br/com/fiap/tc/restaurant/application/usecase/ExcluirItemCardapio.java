package br.com.fiap.tc.restaurant.application.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.domain.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.ItemCardapioRepository;

@Service
public class ExcluirItemCardapio {

    private final ItemCardapioRepository itemCardapioRepository;

    public ExcluirItemCardapio(ItemCardapioRepository itemCardapioRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
    }

    public void execute(Long id) {
        if (!itemCardapioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item de cardápio não encontrado com ID: " + id);
        }
        itemCardapioRepository.deleteById(id);
    }
}
