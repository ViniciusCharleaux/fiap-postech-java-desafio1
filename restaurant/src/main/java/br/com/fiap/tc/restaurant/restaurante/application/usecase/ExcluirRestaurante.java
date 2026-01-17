package br.com.fiap.tc.restaurant.restaurante.application.usecase;

import org.springframework.stereotype.Service;

import br.com.fiap.tc.restaurant.exceptions.ResourceNotFoundException;
import br.com.fiap.tc.restaurant.restaurante.domain.repository.RestauranteRepository;

@Service
public class ExcluirRestaurante {

    private final RestauranteRepository restauranteRepository;

    public ExcluirRestaurante(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public void execute(Long id) {
        if (!restauranteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurante não encontrado com ID: " + id);
        }
        restauranteRepository.deleteById(id);
    }
}
